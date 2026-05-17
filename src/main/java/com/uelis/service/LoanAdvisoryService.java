package com.uelis.service;

import com.uelis.model.LoanApplication;
import com.uelis.model.LoanResult.BankLoanOffer;
import com.uelis.util.EMICalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanAdvisoryService {

    private static final double DTI_LIMIT = 0.45;

    public int approvalConfidence(LoanApplication loan, int aiScore, boolean eligible) {
        int confidence = Math.min(95, Math.max(15, aiScore + 5));

        if (!eligible) {
            confidence = Math.min(confidence, 45);
        }
        if (loan.getCreditScore() >= 750) {
            confidence += 5;
        }
        if (loan.getMonthlyIncome() >= 50000) {
            confidence += 5;
        }
        if (debtToIncome(loan) > 0.35) {
            confidence -= 10;
        }

        return Math.min(98, Math.max(5, confidence));
    }

    public List<String> improvementSuggestions(LoanApplication loan, boolean eligible) {
        List<String> suggestions = new ArrayList<>();

        if (loan.getAge() < 21) {
            suggestions.add("Apply with a co-applicant or wait until the minimum age requirement of 21 is met.");
        } else if (loan.getAge() > 60) {
            suggestions.add("Choose a shorter tenure or add a younger co-applicant to reduce age-related risk.");
        }

        if (loan.getMonthlyIncome() < 15000) {
            double gap = 15000 - loan.getMonthlyIncome();
            suggestions.add("Increase monthly income by at least Rs " + round(gap) + " to meet the minimum income rule.");
        }

        if (loan.getCreditScore() < 650) {
            int gap = 650 - loan.getCreditScore();
            suggestions.add("Improve credit score by " + gap + " points through on-time payments and lower credit utilization.");
        } else if (loan.getCreditScore() < 750) {
            suggestions.add("A credit score of 750 or above can unlock better approval confidence and lower interest rates.");
        }

        double income = loan.getMonthlyIncome();
        if (income > 0) {
            double allowedEmi = income * 0.45;
            if (loan.getExistingEmi() > allowedEmi) {
                double reduceBy = loan.getExistingEmi() - allowedEmi;
                suggestions.add("Reduce existing monthly EMI by about Rs " + round(reduceBy) + " to bring debt-to-income under 45%.");
            }
        }

        double eligibleAmount = income * 0.45 * 36;
        if (eligible && loan.getLoanAmount() > eligibleAmount && eligibleAmount > 0) {
            suggestions.add("Reduce requested amount near Rs " + round(eligibleAmount) + " for a stronger repayment profile.");
        }

        if (suggestions.isEmpty()) {
            suggestions.add("Your profile is strong. Keep EMIs low and maintain payment discipline to preserve approval quality.");
        }

        return suggestions;
    }

    public String whatIfSimulation(LoanApplication loan, boolean eligible) {
        if (eligible) {
            if (loan.getCreditScore() < 750) {
                return "What-if: raising credit score to 750 may move the profile toward premium pricing.";
            }
            return "What-if: reducing the loan amount or extending tenure can lower monthly EMI further.";
        }

        if (loan.getCreditScore() < 650) {
            return "What-if: at credit score 650+, the application can pass the credit threshold if income and EMI rules are also satisfied.";
        }
        if (loan.getMonthlyIncome() < 15000) {
            return "What-if: at monthly income Rs 15,000+, the application can pass the income threshold if other rules remain stable.";
        }
        if (debtToIncome(loan) > 0.45) {
            return "What-if: lowering existing EMIs below 45% of income can turn this profile into an eligible case.";
        }
        return "What-if: adjusting age, income, score, or EMI values can be simulated to find the nearest approval path.";
    }

    public String repaymentPlan(LoanApplication loan, boolean eligible, double interestRate, double emi) {
        if (!eligible) {
            return "Recommended plan: fix the rejection reason first, then reapply with a smaller amount or a co-applicant.";
        }

        double monthlyIncome = loan.getMonthlyIncome();
        if (monthlyIncome <= 0 || interestRate <= 0 || loan.getLoanAmount() <= 0) {
            return "Recommended plan: enter complete loan details to generate a repayment plan.";
        }

        double comfortableEmi = monthlyIncome * 0.35;
        if (emi <= comfortableEmi) {
            return "Recommended plan: current tenure is comfortable because EMI stays within 35% of monthly income.";
        }

        int recommendedTenure = findComfortableTenure(loan.getLoanAmount(), interestRate, comfortableEmi, loan.getTenureMonths());
        if (recommendedTenure > loan.getTenureMonths()) {
            return "Recommended plan: use about " + recommendedTenure + " months tenure to keep EMI closer to 35% of income.";
        }

        double targetAmount = comfortableEmi * loan.getTenureMonths();
        return "Recommended plan: reduce loan amount near Rs " + round(targetAmount) + " or add income support for a safer EMI.";
    }

    public List<BankLoanOffer> bankOffers(LoanApplication loan, boolean eligible) {
        List<BankLoanOffer> offers = new ArrayList<>();

        offers.add(createBankOffer("SBI Bank", loan, eligible, 8.20, 1.00, 5000000));
        offers.add(createBankOffer("HDFC Bank", loan, eligible, 8.65, 0.92, 4500000));
        offers.add(createBankOffer("ICICI Bank", loan, eligible, 8.85, 0.88, 4000000));
        offers.add(createBankOffer("Axis Bank", loan, eligible, 9.10, 0.82, 3500000));
        offers.add(createBankOffer("Canara Bank", loan, eligible, 8.45, 0.95, 3000000));

        return offers;
    }

    public String loanTypeRecommendation(LoanApplication loan) {
        if (debtToIncome(loan) > DTI_LIMIT) {
            return "Recommendation paused: reduce existing EMI first. Banks will not offer a new loan while debt-to-income is above 45%.";
        }
        if (loan.getMonthlyIncome() < 15000 || loan.getCreditScore() < 650) {
            return "Recommendation paused: meet the minimum income and credit score rules before choosing a loan type.";
        }
        if (loan.getCreditScore() >= 750 && loan.getMonthlyIncome() >= 50000) {
            return "Recommended: Home or Auto loan profile. Your credit score can attract lower secured-loan rates.";
        }
        if ("Business".equals(loan.getEmploymentType()) || "Self-Employed".equals(loan.getEmploymentType())) {
            return "Recommended: Business loan with income proof and bank statements for stronger approval.";
        }
        if (loan.getLoanAmount() <= loan.getMonthlyIncome() * 8) {
            return "Recommended: Personal loan. The requested amount is manageable for your income range.";
        }
        return "Recommended: Choose a secured loan or add a co-applicant to improve offered amount.";
    }

    public String affordabilityStatus(LoanApplication loan, double emi) {
        if (loan.getMonthlyIncome() <= 0) {
            return "Affordability: pending. Complete income and loan details to calculate repayment comfort.";
        }

        double currentDti = debtToIncome(loan);
        if (currentDti > DTI_LIMIT) {
            return "Affordability: risky. Existing EMIs already use " + (int) (currentDti * 100)
                    + "% of income, above the 45% limit.";
        }

        if (emi <= 0) {
            return "Affordability: not available until the application becomes eligible for a new loan.";
        }

        double emiRatio = emi / loan.getMonthlyIncome();
        if (emiRatio <= 0.30) {
            return "Affordability: excellent. EMI is within 30% of monthly income.";
        }
        if (emiRatio <= 0.40) {
            return "Affordability: comfortable. EMI is manageable, but keep emergency savings ready.";
        }
        if (emiRatio <= 0.50) {
            return "Affordability: tight. Consider longer tenure or a smaller loan amount.";
        }
        return "Affordability: risky. EMI is too high compared with income.";
    }

    public String prepaymentSavingsTip(LoanApplication loan, double interestRate) {
        if (debtToIncome(loan) > DTI_LIMIT) {
            return "Savings tip: close or reduce an existing EMI first. That is the fastest way to unlock a new loan offer.";
        }
        if (loan.getLoanAmount() <= 0 || loan.getTenureMonths() <= 12 || interestRate <= 0) {
            return "Savings tip: available after the application becomes eligible and EMI is calculated.";
        }

        double originalEmi = EMICalculator.calculateEMI(loan.getLoanAmount(), interestRate, loan.getTenureMonths());
        double originalPayable = originalEmi * loan.getTenureMonths();
        double prepayAmount = loan.getLoanAmount() * 0.10;
        double reducedPrincipal = loan.getLoanAmount() - prepayAmount;
        double revisedEmi = EMICalculator.calculateEMI(reducedPrincipal, interestRate, loan.getTenureMonths());
        double revisedPayable = revisedEmi * loan.getTenureMonths() + prepayAmount;
        double savings = Math.max(0, originalPayable - revisedPayable);

        return "Savings tip: a 10% early prepayment may save about Rs " + round(savings) + " in total repayment.";
    }

    public List<String> requiredDocuments(LoanApplication loan) {
        List<String> documents = new ArrayList<>();
        documents.add("Aadhaar or PAN identity proof");
        documents.add("Last 3 months bank statement");
        documents.add("Address proof");

        if ("Salaried".equals(loan.getEmploymentType())) {
            documents.add("Latest salary slips");
            documents.add("Form 16 or income tax return");
        } else if ("Self-Employed".equals(loan.getEmploymentType())) {
            documents.add("Business registration proof");
            documents.add("Last 2 years income tax returns");
        } else {
            documents.add("Co-applicant income proof");
            documents.add("Education or admission proof, if applicable");
        }

        if ("Home".equals(loan.getLoanType())) {
            documents.add("Property papers and valuation report");
        } else if ("Auto".equals(loan.getLoanType())) {
            documents.add("Vehicle quotation");
        } else if ("Business".equals(loan.getLoanType())) {
            documents.add("Business plan or purpose statement");
        }

        return documents;
    }

    private BankLoanOffer createBankOffer(String bankName, LoanApplication loan, boolean eligible,
                                          double baseRate, double bankMultiplier, double bankCap) {
        if (!eligible || loan.getMonthlyIncome() <= 0 || loan.getTenureMonths() <= 0) {
            return new BankLoanOffer(bankName, 0, baseRate, 0, "No offer until eligibility rules are met");
        }

        double rate = adjustedRate(baseRate, loan);
        double repaymentCapacity = Math.max(0, (loan.getMonthlyIncome() * DTI_LIMIT) - loan.getExistingEmi());
        double profileMultiplier = profileMultiplier(loan);
        double amountByIncome = repaymentCapacity * 36 * bankMultiplier * profileMultiplier;
        double requestedAmount = Math.max(0, loan.getLoanAmount());
        double offeredAmount = Math.min(Math.min(amountByIncome, bankCap), requestedAmount);
        offeredAmount = Math.max(0, Math.round(offeredAmount));

        double estimatedEmi = offeredAmount > 0
                ? EMICalculator.calculateEMI(offeredAmount, rate, loan.getTenureMonths())
                : 0;
        String decision = offeredAmount >= requestedAmount
                ? "Can fund requested amount"
                : "Partial offer based on profile";

        return new BankLoanOffer(bankName, offeredAmount, rate, estimatedEmi, decision);
    }

    private double adjustedRate(double baseRate, LoanApplication loan) {
        double rate = baseRate;
        if (loan.getCreditScore() >= 780) {
            rate -= 0.35;
        } else if (loan.getCreditScore() < 700) {
            rate += 0.50;
        }
        if ("Home".equals(loan.getLoanType())) {
            rate -= 0.25;
        } else if ("Business".equals(loan.getLoanType())) {
            rate += 0.40;
        }
        return Math.round(rate * 100.0) / 100.0;
    }

    private double profileMultiplier(LoanApplication loan) {
        if (loan.getCreditScore() >= 780) {
            return 1.10;
        }
        if (loan.getCreditScore() >= 730) {
            return 1.00;
        }
        if (loan.getCreditScore() >= 680) {
            return 0.88;
        }
        return 0.75;
    }

    private int findComfortableTenure(double amount, double rate, double targetEmi, int currentTenure) {
        for (int months = Math.max(6, currentTenure); months <= 120; months += 6) {
            if (EMICalculator.calculateEMI(amount, rate, months) <= targetEmi) {
                return months;
            }
        }
        return currentTenure;
    }

    private double debtToIncome(LoanApplication loan) {
        if (loan.getMonthlyIncome() <= 0) {
            return 1;
        }
        return loan.getExistingEmi() / loan.getMonthlyIncome();
    }

    private long round(double value) {
        return Math.round(value);
    }
}

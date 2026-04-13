package com.uelis.service;

import com.uelis.model.LoanApplication;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityService {

    public boolean isEligible(LoanApplication loan) {
        return calculateEligibilityReason(loan) == null;
    }

    public String calculateEligibilityReason(LoanApplication loan) {
        if (loan.getAge() < 21)
            return "Applicant must be 21 or older for this loan category.";
        if (loan.getAge() > 60)
            return "Upper age limit for this loan is 60 years.";
        if (loan.getMonthlyIncome() < 15000)
            return "Minimum monthly income of $15,000 required.";
        if (loan.getCreditScore() < 650)
            return "Credit score is below the minimum eligibility threshold of 650.";

        double dti = loan.getExistingEmi() / loan.getMonthlyIncome();
        if (dti > 0.45)
            return "Your debt-to-income ratio (" + (int) (dti * 100) + "%) exceeds the 45% limit.";

        return null;
    }

    public String getReason(LoanApplication loan) {
        String reason = calculateEligibilityReason(loan);
        return (reason != null) ? reason : "Eligible for consideration.";
    }
}

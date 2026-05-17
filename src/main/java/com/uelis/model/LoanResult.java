package com.uelis.model;

import java.util.List;

public class LoanResult {
    private boolean eligible;
    private int aiScore;
    private String risk;
    private String explanation;
    private int approvalConfidence;
    private List<String> improvementSuggestions;
    private String whatIfSimulation;
    private String repaymentPlan;
    private List<BankLoanOffer> bankOffers;
    private String loanTypeRecommendation;
    private String affordabilityStatus;
    private String prepaymentSavingsTip;
    private List<String> requiredDocuments;
    private double emi;
    private double totalPayable;
    private double eligibleAmount;
    private double interestRate;
    private String applicantName;
    private String loanType;
    private double loanAmount;
    private int tenureMonths;

    public LoanResult() {}

    public boolean isEligible() { return eligible; }
    public void setEligible(boolean eligible) { this.eligible = eligible; }

    public int getAiScore() { return aiScore; }
    public void setAiScore(int aiScore) { this.aiScore = aiScore; }

    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public int getApprovalConfidence() { return approvalConfidence; }
    public void setApprovalConfidence(int approvalConfidence) { this.approvalConfidence = approvalConfidence; }

    public List<String> getImprovementSuggestions() { return improvementSuggestions; }
    public void setImprovementSuggestions(List<String> improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }

    public String getWhatIfSimulation() { return whatIfSimulation; }
    public void setWhatIfSimulation(String whatIfSimulation) { this.whatIfSimulation = whatIfSimulation; }

    public String getRepaymentPlan() { return repaymentPlan; }
    public void setRepaymentPlan(String repaymentPlan) { this.repaymentPlan = repaymentPlan; }

    public List<BankLoanOffer> getBankOffers() { return bankOffers; }
    public void setBankOffers(List<BankLoanOffer> bankOffers) { this.bankOffers = bankOffers; }

    public String getLoanTypeRecommendation() { return loanTypeRecommendation; }
    public void setLoanTypeRecommendation(String loanTypeRecommendation) { this.loanTypeRecommendation = loanTypeRecommendation; }

    public String getAffordabilityStatus() { return affordabilityStatus; }
    public void setAffordabilityStatus(String affordabilityStatus) { this.affordabilityStatus = affordabilityStatus; }

    public String getPrepaymentSavingsTip() { return prepaymentSavingsTip; }
    public void setPrepaymentSavingsTip(String prepaymentSavingsTip) { this.prepaymentSavingsTip = prepaymentSavingsTip; }

    public List<String> getRequiredDocuments() { return requiredDocuments; }
    public void setRequiredDocuments(List<String> requiredDocuments) { this.requiredDocuments = requiredDocuments; }

    public double getEmi() { return emi; }
    public void setEmi(double emi) { this.emi = emi; }

    public double getTotalPayable() { return totalPayable; }
    public void setTotalPayable(double totalPayable) { this.totalPayable = totalPayable; }

    public double getEligibleAmount() { return eligibleAmount; }
    public void setEligibleAmount(double eligibleAmount) { this.eligibleAmount = eligibleAmount; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }

    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }

    public static class BankLoanOffer {
        private String bankName;
        private double offeredAmount;
        private double interestRate;
        private double estimatedEmi;
        private String decision;

        public BankLoanOffer() {}

        public BankLoanOffer(String bankName, double offeredAmount, double interestRate, double estimatedEmi, String decision) {
            this.bankName = bankName;
            this.offeredAmount = offeredAmount;
            this.interestRate = interestRate;
            this.estimatedEmi = estimatedEmi;
            this.decision = decision;
        }

        public String getBankName() { return bankName; }
        public void setBankName(String bankName) { this.bankName = bankName; }

        public double getOfferedAmount() { return offeredAmount; }
        public void setOfferedAmount(double offeredAmount) { this.offeredAmount = offeredAmount; }

        public double getInterestRate() { return interestRate; }
        public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

        public double getEstimatedEmi() { return estimatedEmi; }
        public void setEstimatedEmi(double estimatedEmi) { this.estimatedEmi = estimatedEmi; }

        public String getDecision() { return decision; }
        public void setDecision(String decision) { this.decision = decision; }
    }
}

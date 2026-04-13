package com.uelis.model;

public class LoanResult {
    private boolean eligible;
    private int aiScore;
    private String risk;
    private String explanation;
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
}

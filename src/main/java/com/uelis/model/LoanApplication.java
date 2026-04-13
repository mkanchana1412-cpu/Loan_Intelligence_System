package com.uelis.model;

public class LoanApplication {

    private String applicantName;
    private int age;
    private double monthlyIncome;
    private int creditScore;
    private double existingEmi;
    private String loanType;
    private double loanAmount;
    private int tenureMonths;
    private String employmentType;
    private String status;

    public LoanApplication() {}

    public LoanApplication(String applicantName, int age, double monthlyIncome, String employmentType,
                           int creditScore, double existingEmi, String loanType, double loanAmount, int tenureMonths) {
        this.applicantName = applicantName;
        this.age = age;
        this.monthlyIncome = monthlyIncome;
        this.employmentType = employmentType;
        this.creditScore = creditScore;
        this.existingEmi = existingEmi;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.tenureMonths = tenureMonths;
        this.status = "PENDING";
    }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(double monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }

    public double getExistingEmi() { return existingEmi; }
    public void setExistingEmi(double existingEmi) { this.existingEmi = existingEmi; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }

    public int getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(int tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

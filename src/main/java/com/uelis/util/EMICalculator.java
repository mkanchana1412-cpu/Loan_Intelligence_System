package com.uelis.util;

public class EMICalculator {
    public static double calculateEMI(double principal, double annualRate, int tenureMonths) {
        double monthlyRate = annualRate / 12 / 100;
        if (monthlyRate == 0) return principal / tenureMonths;
        
        double emi = principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths) /
                     (Math.pow(1 + monthlyRate, tenureMonths) - 1);
        return Math.round(emi * 100.0) / 100.0;
    }
}

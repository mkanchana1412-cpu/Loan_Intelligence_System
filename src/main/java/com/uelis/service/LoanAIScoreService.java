package com.uelis.service;

import com.uelis.model.LoanApplication;
import org.springframework.stereotype.Service;

@Service
public class LoanAIScoreService {

    public int calculateScore(LoanApplication loan) {
        int score = 0;

        if (loan.getAge() >= 25 && loan.getAge() <= 50)
            score += 20;
        else if (loan.getAge() >= 18)
            score += 10;

        if (loan.getMonthlyIncome() >= 50000)
            score += 30;
        else if (loan.getMonthlyIncome() >= 25000)
            score += 20;
        else if (loan.getMonthlyIncome() >= 15000)
            score += 10;

        if (loan.getCreditScore() >= 750)
            score += 40;
        else if (loan.getCreditScore() >= 650)
            score += 25;
        else if (loan.getCreditScore() >= 600)
            score += 15;

        return score;
    }

    public String riskCategory(int score, int creditScore) {
        if (creditScore >= 750)
            return "LOW RISK";
        if (creditScore >= 650)
            return "MODERATE RISK";
        return "HIGH RISK";
    }
}

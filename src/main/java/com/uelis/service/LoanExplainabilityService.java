package com.uelis.service;

import org.springframework.stereotype.Service;
import com.uelis.model.LoanApplication;

@Service
public class LoanExplainabilityService {

    private final LoanEligibilityService eligibilityService;

    public LoanExplainabilityService(LoanEligibilityService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    public String getReason(LoanApplication loan, int aiScore, boolean eligible) {
        if (!eligible) {
            String rejectionReason = eligibilityService.getReason(loan);
            return "Application Declined: " + rejectionReason;
        }

        if (loan.getCreditScore() >= 750)
            return "Preferred Profile: Excellent credit history and strong income stability. Eligible for premium interest rates.";
        if (loan.getCreditScore() >= 650)
            return "Standard Approval: Solid profile with manageable debt levels. Standard interest rates apply.";

        return "Conditional Approval: Basic eligibility met. Further verification of income stability may be required.";
    }
}

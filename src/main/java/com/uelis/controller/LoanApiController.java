package com.uelis.controller;

import com.uelis.model.LoanApplication;
import com.uelis.model.LoanResult;
import com.uelis.service.LoanAIScoreService;
import com.uelis.service.LoanEligibilityService;
import com.uelis.service.LoanExplainabilityService;
import com.uelis.util.EMICalculator;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoanApiController {

    private final LoanEligibilityService eligibilityService;
    private final LoanAIScoreService aiScoreService;
    private final LoanExplainabilityService explainService;

    public LoanApiController(LoanEligibilityService eligibilityService,
                             LoanAIScoreService aiScoreService,
                             LoanExplainabilityService explainService) {
        this.eligibilityService = eligibilityService;
        this.aiScoreService = aiScoreService;
        this.explainService = explainService;
    }

    /** Health check */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "UELIS Backend");
    }

    /** Main loan assessment endpoint */
    @PostMapping("/loan/check")
    public LoanResult checkLoan(@RequestBody LoanApplication loan) {
        boolean eligible = eligibilityService.isEligible(loan);
        int aiScore     = aiScoreService.calculateScore(loan);
        String risk     = eligible
                ? aiScoreService.riskCategory(aiScore, loan.getCreditScore())
                : "HIGH RISK";
        String explanation = explainService.getReason(loan, aiScore, eligible);

        double emi          = 0;
        double totalPayable = 0;
        double eligibleAmount = 0;
        double rate         = 0;

        if (eligible) {
            rate = (loan.getCreditScore() >= 750) ? 8.5 : 10.5;
            if ("Home".equals(loan.getLoanType())) rate -= 0.5;
            emi           = EMICalculator.calculateEMI(loan.getLoanAmount(), rate, loan.getTenureMonths());
            totalPayable  = Math.round(emi * loan.getTenureMonths() * 100.0) / 100.0;
            eligibleAmount = loan.getMonthlyIncome() * 0.45 * 36;
        }

        LoanResult result = new LoanResult();
        result.setEligible(eligible);
        result.setAiScore(aiScore);
        result.setRisk(risk);
        result.setExplanation(explanation);
        result.setEmi(emi);
        result.setTotalPayable(totalPayable);
        result.setEligibleAmount(eligibleAmount);
        result.setApplicantName(loan.getApplicantName());
        result.setLoanType(loan.getLoanType());
        result.setLoanAmount(loan.getLoanAmount());
        result.setTenureMonths(loan.getTenureMonths());
        result.setInterestRate(rate);
        return result;
    }
}

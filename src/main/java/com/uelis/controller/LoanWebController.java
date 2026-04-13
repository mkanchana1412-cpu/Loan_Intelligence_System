package com.uelis.controller;

import com.uelis.model.LoanApplication;
import com.uelis.service.LoanAIScoreService;
import com.uelis.service.LoanEligibilityService;
import com.uelis.service.LoanExplainabilityService;
import com.uelis.util.EMICalculator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoanWebController {

    @Autowired
    private LoanEligibilityService eligibilityService;
    @Autowired
    private LoanAIScoreService aiScoreService;
    @Autowired
    private LoanExplainabilityService explainService;

    @GetMapping({"/", "/index", "/index.html"})
    public String showForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("loanApplication", new LoanApplication());
        return "index";
    }

    @GetMapping({"/result", "/result.html"})
    public String showResult(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        return "result";
    }

    @GetMapping({"/eligibility", "/eligibility.html"})
    public String showEligibility(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        return "eligibility"; // Changed from eligibility-info to match expected filename or just use eligibility.html
    }

    @GetMapping({"/emi-calculator", "/emi-calculator.html"})
    public String showCalculator(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        return "emi-calculator";
    }

    @GetMapping({"/login", "/login.html"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
            Model model) {
    
        session.setAttribute("loggedIn", true);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/loan/check")
    public String checkLoan(@ModelAttribute LoanApplication loanApplication, Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

     
        boolean eligible = eligibilityService.isEligible(loanApplication);

   
        int aiScore = aiScoreService.calculateScore(loanApplication);
        String risk = eligible ? aiScoreService.riskCategory(aiScore, loanApplication.getCreditScore()) : "HIGH RISK";

        String explanation = explainService.getReason(loanApplication, aiScore, eligible);

   
        double emi = 0;
        double totalPayable = 0;
        double eligibleAmount = 0;
        if (eligible) {
            double rate = (loanApplication.getCreditScore() >= 750) ? 8.5 : 10.5;
      
            if ("Home".equals(loanApplication.getLoanType())) rate -= 0.5;
            
            emi = EMICalculator.calculateEMI(loanApplication.getLoanAmount(), rate, loanApplication.getTenureMonths());
            totalPayable = emi * loanApplication.getTenureMonths();
            eligibleAmount = loanApplication.getMonthlyIncome() * 0.45 * 36; 
        }

        model.addAttribute("loan", loanApplication);
        model.addAttribute("eligible", eligible);
        model.addAttribute("aiScore", aiScore);
        model.addAttribute("risk", risk);
        model.addAttribute("explanation", explanation);
        model.addAttribute("emi", emi);
        model.addAttribute("totalPayable", totalPayable);
        model.addAttribute("eligibleAmount", eligibleAmount);

        return "result";
    }
}

# Unified Loan Intelligence System (Final)

This project is now fully functional and complete. It features an AI-driven credit assessment engine, detailed eligibility logic, and a modern glassmorphism UI.

## Features
- **Smart Assessment**: Calculates an AI score (0-100) based on income, age, and credit history.
- **Explainable AI**: Provides specific reasons for approval or decline.
- **Risk Analysis**: Categorizes applications into Low, Moderate, or High risk.
- **EMI Estimation**: Automatically calculates monthly installments for approved loans.
- **Modern UI**: responsive design with glassmorphism and real-time validation hints.

## Live Demo
GitHub Pages can host only the static frontend, not the Java Spring Boot backend.

- Static demo: https://mkanchana1412-cpu.github.io/Loan_Intelligence_System/
- Full backend app: run locally and open `http://localhost:8080/`

## How to Run Full App Locally
1. **Open the project** in VS Code.
2. **Locate the main class**: [UnifiedLoanApplication.java](file:///c:/Users/KANCHANA/OneDrive/Documents/UnifiedLoanIntelligenceSystem/src/main/java/com/uelis/UnifiedLoanApplication.java).
3. **Run the Application**: Click the "Run" button above the `main` method or press `F5`.
4. **Access the Web Dashboard**: Open your browser and go to:
   **`http://localhost:8080/`**

## Verification
- Enter applicant details in the form.
- Use a high credit score (>750) and income (>5000) to see an **APPROVED** result.
- Use a low age (<18) or low score (<600) to see a **REJECTED** result with an explanation.

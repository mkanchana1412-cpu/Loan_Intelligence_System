# 🎨 UELIS – Frontend (Static HTML/JS)

Pure HTML + CSS + JavaScript. No build step needed!

## How to View

### Method 1 – VS Code Live Server (Recommended ✅)
1. Install the **Live Server** extension in VS Code
2. Right-click `login.html` → **Open with Live Server**
3. The app opens at `http://127.0.0.1:5500/login.html`

### Method 2 – Open file directly in browser
1. Open `login.html` in your browser (double-click or drag)
2. **Note**: The backend must be running for the loan form to work

## Login Credentials
```
Username: admin
Password: admin123
```

## Pages

| File | Description |
|------|-------------|
| `login.html` | Login page (entry point) |
| `index.html` | Loan application form with live simulation |
| `result.html` | Assessment result after form submission |
| `emi-calculator.html` | Standalone EMI calculator (no backend needed) |
| `eligibility.html`    | Eligibility criteria info page |

## Important
- The **backend must be running** at `http://localhost:8080` before submitting the loan form
- The EMI Calculator page works completely offline (no backend required)

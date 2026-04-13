@echo off
echo ===================================================
echo Starting Unified Loan Intelligence System...
echo ===================================================

:: Start the spring boot app in a new command window
start "Unified Loan Server" cmd /c "mvn spring-boot:run"

:: Wait for 10 seconds to let the server initialize
echo Waiting 10 seconds for the Spring Boot server to start...
timeout /t 10 /nobreak > nul

:: Open the default browser to localhost:8080
echo Opening the application in your browser...
start http://localhost:8080

echo Done! The server is running in the background window.

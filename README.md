Selenium BDD Automation Framework with Jenkins & LambdaTest
📌 Project Overview

This project is a real-world automation framework built using Selenium, Cucumber (BDD), and TestNG, fully integrated with Jenkins CI/CD and executed on LambdaTest cloud infrastructure.

The framework supports local and remote execution, secure credential handling, and automated report generation.

🧱 Tech Stack

Language: Java 21

Automation Tool: Selenium WebDriver

BDD Framework: Cucumber

Test Runner: TestNG

Build Tool: Maven

CI/CD: Jenkins

Cloud Platform: AWS EC2 (Amazon Linux)

Cloud Testing: LambdaTest

Version Control: GitHub

⚙️ Framework Features

✔ BDD structure with feature files
✔ Supports Local & Remote execution
✔ Secure credentials via Jenkins Secrets
✔ Remote execution on LambdaTest Grid
✔ CI execution using Jenkins (AWS EC2)
✔ Cucumber & Extent HTML Reports
✔ Screenshot capture on test failure
✔ Configurable via config.properties

🏗 Architecture Flow
GitHub Repository
        ↓
Jenkins (AWS EC2 – Amazon Linux)
        ↓
Maven Build
        ↓
Selenium + Cucumber Tests
        ↓
LambdaTest Cloud Execution
        ↓
Cucumber / Extent Reports

▶️ How to Run on LambdaTest
Update config.properties:


#execution_env=remote
#remote_provider=lambdatest

#browser=chrome
#Applink=https://the-internet.herokuapp.com/

#implicit_wait_seconds=10
#page_load_timeout_seconds=30


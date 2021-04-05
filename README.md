# Test Task Information

### In order to run the project:
* Java, JDK 8+ should be installed
* Maven should be installed.

### Task can be divided into two parts as UI and API:
UI PART:
<p> 1st - Calculator Verification Test
<p> 2nd - Registration Form Verification Test

API PART:
<p> Calculate EndPoint API Verification Test

### **1- Calculator Verification Test**
You will find 4 test methods inside **CalculatorVerificationTest** class.

I tried to cover both negative and positive test scenarios according to requirements in detailed while creating the methods.

Requirements:
<p> - Customer must be able to choose their loan amount and loan period.
<p> - Chosen values must be used in the application process after closing the modal.
<p> - Calculator changes should not be saved before clicking the save button.

All required data for methods are coming from **utility** package -> **Utils** class.
If you need to change any value in the file, it will reflect to the related methods.


* You can run all methods one by one or all together INSIDE CLASS. 
* You can ALSO run class WITH **testng.xml** file. Right click -> Run will be enough. 
After run from testng.xml file, you will also find "AutomationReport.html" file inside reports package.
Once you open these reports with browser, you will find test results, status, screenshots and time stamps inside the report.
* After all methods execution, you can find screenshots from the last view inside **reports** folder according to name of method.

(If you want to execute them from terminal please refer "4- Execute all test cases in Terminal" section)

### **2- Registration Form Verification Test**
You will find 3 test methods inside **RegistrationFormVerificationTest** class.

I tried to cover to check error messages on the form, text validation field messages and button activity in detailed while creating the methods.

All required data for methods are coming from **utility** package -> **Utils** class.
If you need to change any value in the file, it will reflect to the related methods.

* You can run all methods one by one or all together INSIDE CLASS. 
* You can ALSO run class WITH **testng.xml** file. Right click -> Run will be enough. 
After run from testng.xml file, you will also find "AutomationReport.html" file inside reports package.
Once you open these reports with browser, you will find test results, status, screenshots and time stamps inside the report.
* After all methods execution, you can find screenshots from the last view inside **reports** folder according to name of method.

(If you want to execute them from terminal please refer "4- Execute all test cases in Terminal" section)

### **3- Calculate EndPoint API Verification Test**
You will find 3 test methods inside **CalculateEndPointAPIVerificationTest** class.

I tried to cover to calculator API requirements with different outcomes (which contains both valid and border values) in detailed while creating the methods.

Requirements:
<p> - If loan amount or period is changed, then new monthly payment amount and APRC(Annual Percentage Rate of Charge) will be calculated by "calculate" endpoint.
<p> - Create test set for different "calculate" endpoint outcomes for monthly payment and APRC.

I created "monthlyPaymentCalculator" myself and give you a reference. 
It could be replaced by BigBank Calculator to verify the result because you can see result difference.

* You can run all methods one by one or all together INSIDE CLASS. 

### **4- Execute all test cases in Terminal**
Go to terminal:
- `mvn clean` : to clean the code
- `mvn compile` : to check the syntax error
- `mvn test` : to test code

!!!! ATTENTION ⇒ to execute just ONE test class command is given below:
- `mvn -Dtest = CalculatorVerificationTest test`





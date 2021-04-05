package org.BigBank;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.BigBank.utility.Utils.*;

public class RegistrationFormVerificationTest extends AbstractTest {

    @BeforeMethod
    public void setUp() {
        super.setUp();
        driver.manage().window().maximize();
        driver.get(LOAN_URL);
    }

    @BeforeTest
    public ExtentReports config() {
        String path = System.getProperty("user.dir") + "/reports/AutomationReport.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Automation Test Results");
        reporter.config().setDocumentTitle("Automation Test Results");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Pelin Kacar");
        return extent;
    }

    @Test
    public void verify_required_field_messages_are_exist() throws IOException {
        clickWithoutFillingTheRegistrationForm();
        checkFieldMessageIsExist(FIRST_NAME_MESSAGE_FIELD);
        checkFieldMessageIsExist(SUR_NAME_MESSAGE_FIELD);
        checkFieldMessageIsExist(PERSONAL_IDENTITY_CODE_MESSAGE_FIELD);
        checkFieldMessageIsExist(EMAIL_MESSAGE_FIELD);
        checkFieldMessageIsExist(PHONE_MESSAGE_FIELD);
        checkFieldMessageIsExist(LOAN_PURPOSE_MESSAGE_FIELD);
        checkContinueButtonNotActive();

        takeScreenShot("verify_required_field_messages_are_exist");
    }

    @Test
    public void verify_text_validation_field_messages_are_exist() throws IOException {
        firstName().sendKeys(INVALID_NAME_WITH_LETTER);
        firstName().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(FIRST_NAME_MESSAGE_FIELD);

        firstName().sendKeys(INVALID_NAME_WITH_NUMBER);
        firstName().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(FIRST_NAME_MESSAGE_FIELD);

        surName().sendKeys(INVALID_NAME_WITH_LETTER);
        surName().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(SUR_NAME_MESSAGE_FIELD);

        surName().sendKeys(INVALID_NAME_WITH_NUMBER);
        surName().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(SUR_NAME_MESSAGE_FIELD);

        personalIdentityCode().sendKeys(INVALID_PERSONAL_NUMBER);
        personalIdentityCode().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(PERSONAL_IDENTITY_CODE_MESSAGE_FIELD);

        email().sendKeys(INVALID_EMAIL);
        email().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(EMAIL_MESSAGE_FIELD);

        phone().sendKeys(INVALID_PHONE);
        phone().sendKeys(Keys.ENTER);
        checkFieldMessageIsExist(PHONE_MESSAGE_FIELD);

        takeScreenShot("verify_text_validation_field_messages_are_exist");

    }

    @Test
    public void verify_continue_button_is_active_after_filling_the_registration_form_with_valid_info() throws IOException {
        fillRegistrationForWithValidInfo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkContinueButtonIsActive();

        takeScreenShot("verify_continue_button_is_active_after_filling_the_registration_form_with_valid_info");

    }

    @AfterMethod
    public void teardown() {
        driver.close();
    }

    private void fillRegistrationForWithValidInfo() {
        firstName().sendKeys(FIST_NAME);
        surName().sendKeys(LAST_NAME);
        personalIdentityCode().sendKeys(VALID_PERSONAL_NUMBER);
        email().sendKeys(VALID_EMAIL);
        phone().sendKeys(VALID_PHONE);
        Select loanPurpose = new Select(loanPurpose());
        loanPurpose.selectByIndex(1);

    }

    private WebElement firstName() {
        return driver.findElement(By.id("firstNameField"));
    }

    private WebElement surName() {
        return driver.findElement(By.id("surnameField"));
    }

    private WebElement personalIdentityCode() {
        return driver.findElement(By.id("personalIdentityCodeField"));
    }

    private WebElement email() {
        return driver.findElement(By.id("emailField"));
    }

    private WebElement phone() {
        return driver.findElement(By.name("phone"));
    }

    private WebElement loanPurpose() {
        return driver.findElement(By.id("loanPurposeField"));
    }

    private void clickWithoutFillingTheRegistrationForm() {
        firstName().click();
        firstName().sendKeys(Keys.ENTER);
    }

    private void checkFieldMessageIsExist(int numberOfIndex) {
        String firstNameRequiredMessage = driver.findElement(By.xpath("//form[@id='personalDetailsForm']/div[" + numberOfIndex + "]/p[1]")).getText();
        Assert.assertFalse(firstNameRequiredMessage.isEmpty());
    }

    private void checkContinueButtonNotActive(){
        String activeContinueButton = driver.findElement(By.id("personalDetailsForm-submit")).getText();
        Assert.assertFalse(activeContinueButton.contains("Fortsätt"));

    }

    private void checkContinueButtonIsActive(){
        String activeContinueButton = driver.findElement(By.id("personalDetailsForm-submit")).getText();
        System.out.println(activeContinueButton);
        Assert.assertTrue(activeContinueButton.contains("FORTSÄTT"));

    }
}



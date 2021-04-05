package org.BigBank;

import org.BigBank.utility.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.BigBank.utility.Utils.*;

public class CalculatorVerificationTest extends AbstractTest {

    @BeforeMethod
    public void setUp() {
        super.setUp();
        driver.manage().window().maximize();
        driver.get(Utils.LOAN_URL);
    }

    @Test
    public void verify_monthly_payment_amount_changed_after_customer_selected_the_loan_amount() throws IOException, InterruptedException {
        clickForCalculator();
        changeLoanAmount(NEW_LOAN_AMOUNT);
        Assert.assertNotEquals(monthlyCost(),DEFAULT_MONTHLY_COST);
        Thread.sleep(2000);
        takeScreenShot("verify_monthly_payment_amount_changed_after_customer_selected_the_loan_amount");
    }

    @Test
    public void verify_monthly_payment_amount_changed_after_customer_selected_the_loan_period() throws IOException, InterruptedException {
        clickForCalculator();
        changeLoanPeriod(NEW_LOAN_PERIOD);
        Assert.assertNotEquals(monthlyCost(),DEFAULT_MONTHLY_COST);
        Thread.sleep(2000);
        takeScreenShot("verify_monthly_payment_amount_changed_after_customer_selected_the_loan_period");
    }

    @Test
    public void verify_calculator_changes_did_not_save_before_clicking_apply_button() throws IOException {
        clickForCalculator();
        changeLoanAmount(NEW_LOAN_AMOUNT);
        changeLoanPeriod(NEW_LOAN_PERIOD);
        clickExitButton();
        Assert.assertNotEquals(loanAmount(),NEW_LOAN_AMOUNT);

        takeScreenShot("verify_calculator_changes_did_not_save_before_clicking_apply_button");
    }

    @Test
    public void verify_calculator_changes_saved_after_clicking_apply_button() throws IOException {
        clickForCalculator();
        changeLoanAmount(NEW_LOAN_AMOUNT);
        changeLoanPeriod(NEW_LOAN_PERIOD);
        clickApplyButton();
        Assert.assertEquals(loanAmount(),NEW_LOAN_AMOUNT);

        takeScreenShot("verify_calculator_changes_saved_after_clicking_apply_button");

    }

    @AfterMethod
    public void teardown() {
        driver.close();
    }

    private void clickForCalculator(){
        driver.findElement(By.xpath("//*[@class='bb-edit-amount__content']")).click();

    }

    private void changeLoanAmount(String amount){
        WebElement editAmount = driver.findElement(By.xpath("//div[@name='calculator-amount']/div/button"));
        editAmount.click();
        WebElement amountField = driver.findElement(By.xpath("//div[@id='header-calculator']/div/div[2]/div/input"));
        amountField.sendKeys(Keys.DELETE);
        amountField.sendKeys(amount);
        amountField.sendKeys(Keys.ENTER);
    }

    private void changeLoanPeriod(String period){
        WebElement editPeriod = driver.findElement(By.xpath("//div[@name='calculator-period']/div/button"));
        editPeriod.click();
        WebElement periodField = driver.findElement(By.xpath("//div[@id='header-calculator']/div[2]/div[2]/div/input"));
        periodField.sendKeys(Keys.DELETE);
        periodField.sendKeys(period);
        periodField.sendKeys(Keys.ENTER);

    }

    private void clickApplyButton(){
        driver.findElement(By.xpath("//span[contains(text(),'Ansök nu')]")).click();
    }

    private void clickExitButton(){
        driver.findElement(By.xpath("//div[@class='bb-modal__header']/button")).click();
    }

    private String loanAmount(){
        return driver.findElement(By.xpath("//*[@class='bb-edit-amount__amount']")).getText();
    }

    private String monthlyCost(){
        return driver.findElement(By.xpath("//*[@class='bb-calculator__result-value']")).getText().substring(0,7);
    }

}



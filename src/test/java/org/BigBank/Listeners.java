package org.BigBank;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

    static ExtentReports extent = new ExtentReports();

    @Override
    public void onTestSuccess(ITestResult result) {
        attachResultToReport(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachResultToReport(result);
    }


    private void attachResultToReport(ITestResult result) {
        String name = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(name);
        if (result.isSuccess()) {
            test.pass("Test Passed");
        } else {
            test.fail(result.getThrowable().getMessage());
        }
        test.addScreenCaptureFromPath(name + ".png");
        extent.flush();
    }
}

package org.BigBank;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

import static org.BigBank.utility.Utils.DRIVE_PATH;
import static org.BigBank.utility.Utils.WEBDRIVER_INFO;

public abstract class AbstractTest extends Listeners {

    WebDriver driver;

    public void setUp() {
        System.setProperty(WEBDRIVER_INFO, DRIVE_PATH);
        this.driver = new ChromeDriver();
    }

    public void takeScreenShot(String name) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./reports/" + name + ".png"));
    }
}
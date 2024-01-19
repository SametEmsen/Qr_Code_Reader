package test;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.BrowserUtils;
import utils.Driver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest extentLogger;

    @BeforeMethod
    public void setUp() {
        driver = Driver.get();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(3500, 0));
        wait = new WebDriverWait(driver, 7);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        //if Test fails
        if (result.getStatus() == ITestResult.FAILURE) {
            //Record the name of the failed test
            extentLogger.fail(result.getName());
            //Take the screenshot and return the location of screenshot
            String screenShotPath = BrowserUtils.getScreenshot(result.getName());
            //Add the screenshot to the report
            extentLogger.addScreenCaptureFromPath(screenShotPath);
            //Capture the exception and put inside the report
            extentLogger.fail(result.getThrowable());
        }
        Driver.closeDriver();
    }
}

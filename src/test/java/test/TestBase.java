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
// name the screenshot with the current date time to avoid duplicate name
        if (result.getStatus() == ITestResult.FAILURE) {
// TakesScreenshot ---> interface from selenium which takes screenshots
            extentLogger.fail(result.getName());
//   ((TakesScreenshot)Driver.get()).getScreenshotAs(OutputType.FILE);
            String screenShotPath = BrowserUtils.getScreenshot(result.getName());
// full path to the screenshot location
            extentLogger.addScreenCaptureFromPath(screenShotPath);
            // save the screenshot to the path given
            extentLogger.fail(result.getThrowable());
        }
        Driver.closeDriver();
    }
}

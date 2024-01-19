package test;

import com.google.zxing.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BrowserUtils;
import utils.ConfigurationReader;

import java.io.IOException;

public class QrCodeReader extends TestBase{
    @Test
    public void qrCodeReaderTest() throws NotFoundException, IOException {
        //gets the url from properties file
        driver.get(ConfigurationReader.get("url"));

        //locates the textBox
        WebElement textInputBox = driver.findElement(By.id("qrcode-field-text-text"));

        //Here is your choosen text, please edit for your desire
        String text="Here is your given text";

        //sends text to textBox
        textInputBox.sendKeys(text);

        //// Tabs out of the text box to trigger QR code generation
        textInputBox.sendKeys(Keys.TAB);

        //waits one sec
        BrowserUtils.waitFor(1);

        //gets the generated qr code url
        String qrCodeUrl = driver.findElement(By.id("qrcode-preview-image")).getAttribute("src");

        //this method converts qr codes image to text
        String actualQrText = BrowserUtils.qrCodeeReader(qrCodeUrl);

        //verifies that code is worked as expected
        Assert.assertEquals(actualQrText,text);
    }
}

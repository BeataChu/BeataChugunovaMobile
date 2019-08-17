package native_tests;

import entities.Driver;
import entities.TestProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import hooks.Hooks;

/**
 * Set of tests for native application
 */
public class SimpleNativeTests extends Hooks {

    @Test(description = "Click the button 'Add contact'", groups = {"native"})
    public void simplestTest() throws Exception {
        String udid = TestProperties.getProperty("udid");
        String cloudURL = TestProperties.getProperty("installationURL");
        System.out.println("cloudURL = " + cloudURL);
        System.out.println("udid = " + udid);
        String filePath = System.getProperty("user.dir") + TestProperties.getProperty("aut");
        System.out.println("filePath = " + filePath);
        CloudClient.remoteInstall(cloudURL, udid, filePath);


        String app_package_name = "com.example.android.contactmanager:id/";
        By add_btn = By.id(app_package_name + "addContactButton");
        Driver.getInstance().appiumDriver().findElement(add_btn).click();


        //Add Contact Page is opened
        By addContactXPath = By.xpath("//android.widget.TextView[@text=\"Add Contact\"]");
        WebElement addBtn = Driver.getInstance().driverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(addContactXPath));

        //Assert that Add Contact Page has all required elements
        assert (addBtn.isDisplayed());
        WebElement targetAccountHeader = Driver.getInstance().appiumDriver()
                .findElementByAccessibilityId("Target Account");
        assert (targetAccountHeader.isDisplayed());
        WebElement contactNameHeader = Driver.getInstance().appiumDriver()
                .findElementByAccessibilityId("Contact Name");
        assert (contactNameHeader.isDisplayed());
        WebElement contactPhoneHeader = Driver.getInstance().appiumDriver()
                .findElementByAccessibilityId("Contact Phone");
        assert (contactPhoneHeader.isDisplayed());
        WebElement saveButton = Driver.getInstance().appiumDriver()
                .findElementByAccessibilityId("Save");
        assert (saveButton.isDisplayed());

        System.out.println("Simple native test is done");
    }
}

package hw2.native_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import hw2.Hooks;
import hw2.Driver;

/**
 * Set of tests for native application
 */
public class SimpleNativeTests extends Hooks {

    @Test(description = "Click the button 'Add contact'")
    public void simplestTest() throws Exception {
        String app_package_name = "com.example.android.contactmanager:id/";
        By add_btn = By.id(app_package_name + "addContactButton");
        Driver.appiumDriver().findElement(add_btn).click();


        //Add Contact Page is opened
        //Assert that Add Contact Page has all required elements
        WebElement addBtn = Driver.driverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Add Contact\"]")));
//        Driver.appiumDriver().findElementByXPath("//android.widget.TextView[@text=\"Add Contact\"]");
        assert (addBtn.isDisplayed());
        WebElement targetAccountHeader = Driver.appiumDriver().findElementByAccessibilityId("Target Account");
        assert (targetAccountHeader.isDisplayed());
        WebElement contactNameHeader = Driver.appiumDriver().findElementByAccessibilityId("Contact Name");
        assert (contactNameHeader.isDisplayed());
        WebElement contactPhoneHeader = Driver.appiumDriver().findElementByAccessibilityId("Contact Phone");
        assert (contactPhoneHeader.isDisplayed());
        WebElement saveButton = Driver.appiumDriver().findElementByAccessibilityId("Save");
        assert (saveButton.isDisplayed());

        System.out.println("Simplest Appium test is done");
    }
}

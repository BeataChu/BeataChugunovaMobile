package web_tests;

import entities.TestProperties;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import hooks.Hooks;
import entities.Driver;

import static org.testng.Assert.assertEquals;

/**
 * Set of tests for web application
 */
public class SimpleWebTests extends Hooks {

    @Test(description = "Open website")
    public void webTest() throws Exception {
        String siteName = Driver.getInstance().getSUT();
        Driver.getInstance().appiumDriver().get(siteName);
        Driver.getInstance().driverWait().until(ExpectedConditions.urlToBe(siteName + "/"));
        String actualPageTitle = Driver.getInstance().appiumDriver().getTitle();
        String expectedPageTitle = TestProperties.getProperty("mainPageTitle");
        assertEquals(actualPageTitle,expectedPageTitle);
        System.out.println("Site opening done");
    }
}

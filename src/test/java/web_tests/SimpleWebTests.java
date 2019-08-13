package web_tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import hooks.Hooks;
import entities.Driver;

/**
 * Set of tests for web application
 */
public class SimpleWebTests extends Hooks {

    @Test(description = "Open website")
    public void webTest() throws Exception {
        String siteName = Driver.getSUT();
        Driver.appiumDriver().get(siteName);
        Driver.driverWait().until(ExpectedConditions.urlToBe(siteName + "/"));
        assert(Driver.appiumDriver().getTitle().equals("Internet Assigned Numbers Authority"));
        System.out.println("Site opening done");
    }
}

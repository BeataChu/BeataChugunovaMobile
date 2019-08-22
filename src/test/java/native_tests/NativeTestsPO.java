package native_tests;

import entities.Driver;
import entities.page_objects.AddContactPage;
import entities.page_objects.HomePage;
import hooks.Hooks;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class NativeTestsPO extends Hooks {


    @Test(description = "Click the button 'Add contact'", groups = { "native" })
    public void nativeTestWithPageObjects() throws Exception {
        HomePage homePage = new HomePage(Driver.getInstance().appiumDriver());


        //Check that button "Add Contact" on the home page is visible and click it
        assert (homePage.getHomeAddButton().isDisplayed());
        homePage.clickHomeAddButton();

        //Add Contact Page is opened
        //Assert that Add Contact Page has all required elements
        AddContactPage addContactPage = new AddContactPage(Driver.getInstance().appiumDriver());
        Driver.getInstance().driverWait().until(ExpectedConditions.visibilityOf(addContactPage.getAddContactTitle()));
        assert (addContactPage.getTargetAccountHeader().isDisplayed());
        assert (addContactPage.getContactNameHeader().isDisplayed());
        assert (addContactPage.getContactPhoneHeader().isDisplayed());
        assert (addContactPage.getSaveBtn().isDisplayed());

        System.out.println("Simple native test is done");
    }
}

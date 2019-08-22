package entities.page_objects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddContactPage extends BasePage {

    public AddContactPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//android.widget.TextView[@text=\"Add Contact\"]")
    private WebElement addContactTitle;

    @AndroidFindBy(accessibility = "Target Account")
    private WebElement targetAccountHeader;

    @AndroidFindBy(accessibility = "Contact Name")
    private WebElement contactNameHeader;

    @AndroidFindBy(accessibility = "Contact Phone")
    private WebElement contactPhoneHeader;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveBtn;

    public WebElement getAddContactTitle() {
        return addContactTitle;
    }

    public WebElement getTargetAccountHeader() {
        return targetAccountHeader;
    }

    public WebElement getContactNameHeader() {
        return contactNameHeader;
    }

    public WebElement getContactPhoneHeader() {
        return contactPhoneHeader;
    }

    public WebElement getSaveBtn() {
        return saveBtn;
    }
}
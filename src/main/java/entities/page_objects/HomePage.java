package entities.page_objects;

import entities.TestProperties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver){
        super(driver);
    }

    private WebElement homeAddButton = driver.findElement(By.id(TestProperties.getProperty("appPackage") + ":id/addContactButton"));

    public WebElement getHomeAddButton(){
        return homeAddButton;
    }

   public void clickHomeAddButton(){
        homeAddButton.click();
   }
}


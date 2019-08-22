package entities.page_objects;

import entities.TestProperties;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    private WebElement homeAddButton = driver.findElement(By.id(TestProperties.getProperty("appPackage") + ":id/addContactButton"));

    public WebElement getHomeAddButton() {
        return homeAddButton;
    }

    public void clickHomeAddButton() {
        homeAddButton.click();
    }
}
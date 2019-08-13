package entities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

public class Driver {
    private static Driver driver = null;
    private static AppiumDriver driverSingle = null;
    private static WebDriverWait waitSingle;
    protected DesiredCapabilities capabilities;


    private String AUT; //app under testing
    private String SUT; //site under testing
    private String TEST_PLATFORM;
    private String DRIVER;
    private String DEVICE_NAME;

    /**
     * Driver constructor
     *
     * @throws Exception
     */
    private Driver() throws Exception {

        AUT = TestProperties.getProperty("aut");
        String t_sut = TestProperties.getProperty("sut");
        SUT = t_sut == null ? null : "http://" + t_sut;
        TEST_PLATFORM = TestProperties.getProperty("platform");
        DRIVER = TestProperties.getProperty("driver");
        DEVICE_NAME = TestProperties.getProperty("deviceName");
    }

    /**
     * Initialize driver with appropriate capabilities depending on platform and application
     *
     * @throws Exception
     */
    protected static void instantiate() throws Exception {
        driver = new Driver();
        driver.capabilities = new DesiredCapabilities();
        driver.capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

        String browserName;

        // Setup test platform: Android or iOS. Browser also depends on a platform.
        switch (driver.TEST_PLATFORM) {
            case "Android":
                driver.capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, driver.DEVICE_NAME);
                browserName = "Chrome";
                break;
            case "iOS":
                browserName = "Safari";
                break;
            default:
                throw new Exception("Unknown mobile platform");
        }
        driver.capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, driver.TEST_PLATFORM);

        // Setup type of application: mobile, web (or hybrid)
        if (driver.AUT != null && driver.SUT == null) {
            //  Native
            File app = new File(System.getProperty("user.dir") + driver.AUT);
            driver.capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        } else if (driver.SUT != null && driver.AUT == null) {
            // Web
            driver.capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
        } else {
            throw new Exception("Unclear type of mobile app");
        }

        // Init driver for local Appium server with set of capabilities
        if (driverSingle == null) {
            driverSingle = new AppiumDriver(new URL(driver.DRIVER), driver.capabilities);
        }
        //Set an object to handle timeouts
        if (waitSingle == null) {
            waitSingle = new WebDriverWait(appiumDriver(), 10);
        }
    }

    /**
     * Realisation of a singleton pattern for an Appium driver
     *
     * @return instance of Appium driver
     * @throws Exception
     */
    public static AppiumDriver appiumDriver() throws Exception {
        if (driverSingle == null) instantiate();
        return driverSingle;
    }

    /**
     * Realisation of a singleton pattern for a wait Object
     *
     * @return instance of a wait object
     * @throws Exception
     */
    public static WebDriverWait driverWait() throws Exception {
        if (waitSingle == null) instantiate();
        return waitSingle;
    }

    /**
     * Ends life cycle of a driver and it's fields
     */
    public static void quit() {
        if (driverSingle == null) {
            throw new NullPointerException("driver has not been instantiated");
        }
        driverSingle.quit();
        waitSingle = null;
        driver = null;

    }

    /**
     * return a path to a site under test
     *
     * @return driver.SUT
     * @throws Exception
     */
    public static String getSUT() throws Exception {
        if (driverSingle == null) instantiate();
        return driver.SUT;
    }

}

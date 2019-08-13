package entities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

public class Driver {
    private static Driver driver = null;
    private AppiumDriver driverSingle = null;
    private WebDriverWait waitSingle;
    protected DesiredCapabilities capabilities;


    private String AUT; //app under testing
    private String SUT; //site under testing
    private String TEST_PLATFORM;
    private String DRIVER_URL;
    private String UDID;
    private String AUTOMATION_NAME;

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
        DRIVER_URL = TestProperties.getProperty("driver_url");
        AUTOMATION_NAME = TestProperties.getProperty("automation");
        UDID = TestProperties.getProperty("udid");
    }

    /**
     * Initialize driver with appropriate capabilities depending on platform and application
     *
     * @throws Exception
     */
    protected static void instantiate() throws Exception {
        driver = new Driver();
        driver.capabilities = new DesiredCapabilities();

        String browserName;

        // Setup test platform: Android or iOS. Browser also depends on a platform.
        switch (driver.TEST_PLATFORM) {
            case "Android":
//                driver.capabilities.setCapability(MobileCapabilityType.UDID, driver.UDID);
                browserName = "Chrome";
                break;
            case "iOS":
//                driver.capabilities.setCapability(MobileCapabilityType.UDID, driver.UDID);
                browserName = "Safari";
                break;
            default:
                throw new Exception("Unknown mobile platform");
        }
        driver.capabilities.setCapability(MobileCapabilityType.UDID, driver.UDID);
        driver.capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, driver.TEST_PLATFORM);
        driver.capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, driver.AUTOMATION_NAME);

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
        try {
            if (driver.driverSingle == null) {
                driver.driverSingle = new AppiumDriver(new URL(driver.DRIVER_URL), driver.capabilities);
            }
            //Set an object to handle timeouts
            if (driver.waitSingle == null) {
                driver.waitSingle = new WebDriverWait(appiumDriver(), 10);
            }
        } catch (WebDriverException wde) {
            System.out.println("Try to switch Appium on");
            wde.printStackTrace();
        }
    }

    /**
     * Realisation of a singleton pattern for an Appium driver
     *
     * @return instance of Appium driver
     * @throws Exception
     */
    public static AppiumDriver appiumDriver() throws Exception {
        if (driver == null) instantiate();
        return driver.driverSingle;
    }

    /**
     * Realisation of a singleton pattern for a wait Object
     *
     * @return instance of a wait object
     * @throws Exception
     */
    public static WebDriverWait driverWait() throws Exception {
        if (driver == null) instantiate();
        return driver.waitSingle;
    }

    /**
     * Ends life cycle of a driver and it's fields
     */
    public static void quit() {
        if (driver == null) {
            throw new NullPointerException("Driver has not been instantiated");
        }
        driver.driverSingle.quit();
        driver.waitSingle = null;
        driver = null;

    }

    /**
     * return a path to a site under test
     *
     * @return driver.SUT
     * @throws Exception
     */
    public static String getSUT() throws Exception {
        if (driver == null) instantiate();
        return driver.SUT;
    }

}

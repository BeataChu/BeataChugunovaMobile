package entities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
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
    private String APP_PACKAGE;
    private String APP_ACTIVITY;


    /**
     * Driver constructor
     *
     * @throws Exception
     */
    private Driver() throws Exception {
        AUT = TestProperties.getProperty("aut");
        String t_sut = TestProperties.getProperty("sut");
        SUT = t_sut == null ? null : "https://" + t_sut;
        TEST_PLATFORM = TestProperties.getProperty("platform");
        DRIVER_URL = String.format(TestProperties.getProperty("driver_url"), System.getenv("CLOUD_TOKEN"));
        AUTOMATION_NAME = TestProperties.getProperty("automation");
        UDID = TestProperties.getProperty("udid");
        APP_PACKAGE = TestProperties.getProperty("appPackage");
        APP_ACTIVITY = TestProperties.getProperty("appActivity");
    }

    /**
     * Initialize driver with appropriate capabilities depending on platform and application
     *
     * @throws Exception
     */
    public static Driver getInstance() throws Exception {
        if (driver == null) {
            driver = new Driver();
            driver.capabilities = new DesiredCapabilities();

            String browserName;

            // Setup test platform: Android or iOS. Browser also depends on a platform.
            switch (driver.TEST_PLATFORM) {
                case "Android":
                    driver.capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, driver.APP_PACKAGE);
                    driver.capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, driver.APP_ACTIVITY);
                    driver.capabilities.setCapability("autoLaunch", "true");
                    browserName = "Chrome";
                    break;
                case "iOS":
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
            if (driver.driverSingle == null) {
                boolean isConnected = false;
                int tries = 0;
                int maxTries = 3;
                while (!isConnected && tries < maxTries) {
                    try {
                        driver.driverSingle = new AppiumDriver(new URL(driver.DRIVER_URL), driver.capabilities);
                        isConnected = true;
                    } catch (Exception e) {
                        System.out.println("Trying to connect to driver, attempt " + (tries + 1));
                    }
                    tries++;
                }
                if (!isConnected) {
                    throw new Exception("Couldn't connect to driver.");
                }
            }
            //Set an object to handle timeouts
            if (driver.waitSingle == null) {
                driver.waitSingle = new WebDriverWait(driver.appiumDriver(), 10);
            }

        }
        return driver;
    }

    /**
     * Realisation of a singleton pattern for an Appium driver
     *
     * @return instance of Appium driver
     * @throws Exception
     */
    public AppiumDriver appiumDriver() throws Exception {
        return driverSingle;
    }

    /**
     * Realisation of a singleton pattern for a wait Object
     *
     * @return instance of a wait object
     * @throws Exception
     */
    public WebDriverWait driverWait() throws Exception {
        return waitSingle;
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
    public String getSUT() throws Exception {
        return SUT;
    }
}

package hooks;

import entities.Driver;
import entities.PropertiesType;
import entities.TestProperties;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Properties;

/**
 * Class of hooks
 */
public class Hooks {

    /**
     * Setting up test by instantiating test properties
     *
     * @throws Exception
     */

    @BeforeSuite(description = "Prepare driver for test running")
    @Parameters({"testType", "platform", "udid"})
    public void setUp(@Optional("web")String testType, @Optional("iOS") String platform, @Optional("ec645b7fe329a5ee0b53bd3494ad2cd8963ec07a") String udid) throws Exception {
        System.out.println(String.format("Passed parameters: testType = %s, platform = %s, udid = %s", testType, platform, udid));
        PropertiesType type;
        switch (testType) {
            case ("web"):
                type = (platform.equals("Android"))? PropertiesType.WEB_ANDROID_PROPS : PropertiesType.WEB_IOS_PROPS;
                break;
            case ("native"):
                type = (platform.equals("Android"))? PropertiesType.NATIVE_ANDROID_PROPS : PropertiesType.NATIVE_IOS_PROPS;
                break;
            default:
                throw new IllegalArgumentException("No such test type");
        }
        TestProperties.instantiate(type, udid);
    }

    /**
     * Tearing down test by ending life cycle of a driver and test properties ovject
     *
     * @throws Exception
     */
    @AfterSuite(description = "Close driver on all tests completion")
    public void tearDown() throws Exception {
        Driver.quit();
        TestProperties.erase();
    }
}

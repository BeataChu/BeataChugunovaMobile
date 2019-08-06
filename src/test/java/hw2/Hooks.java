package hw2;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

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
    @Parameters({"testType", "deviceName"})
    public void setUp(@Optional("web")String testType, @Optional("emulator-5554") String deviceName) throws Exception {
        System.out.println(String.format("Passed parameters: testType = %s, deviceName = %s", testType, deviceName));
        switch (testType) {
            case ("web"):
                TestProperties.instantiate(PropertiesType.WEB_PROPS, deviceName);
                break;
            case ("native"):
                TestProperties.instantiate(PropertiesType.NATIVE_PROPS, deviceName);
                break;
            default:
                throw new IllegalArgumentException("No such test type");
        }
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

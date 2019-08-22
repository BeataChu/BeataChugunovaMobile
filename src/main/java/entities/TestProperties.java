package entities;

import java.io.FileInputStream;
import java.util.Properties;


/**
 * Singleton object of test properties
 */
public class TestProperties {
    private static TestProperties testProperties;
    private static int instanceCounter = 0;

    private Properties currentProps = new Properties();
    private PropertiesType type;

    private TestProperties() {
    }

    /**
     * Instantiates properties depending on tests type
     *
     * @param type
     * @throws Exception
     */
    public static void instantiate(PropertiesType type, String deviceName) throws Exception {
        if (instanceCounter != 0) {
            throw new InstantiationException("Test properties have already been instantiated");
        }
        testProperties = new TestProperties();
        testProperties.type = type;
        FileInputStream in = new FileInputStream((System.getProperty("user.dir") + type.getPath()));
        testProperties.currentProps.load(in);
        testProperties.currentProps.setProperty("deviceName", deviceName);
        in.close();
        instanceCounter++;
    }

    /**
     * Returns required property
     *
     * @param propKey
     * @return property;
     * @throws Exception
     */
    public static String getProperty(String propKey) {
        if (testProperties == null) {
            throw new NullPointerException("Test properties have not been instantiated");
        }
        return testProperties.currentProps.getProperty(propKey, null);
    }

    /**
     * Ends life cycle of a properties object
     */
    public static void erase() {
        if (testProperties == null) {
            throw new NullPointerException("Test properties have not been instantiated");
        }
        testProperties = null;
        instanceCounter = 0;
    }

}

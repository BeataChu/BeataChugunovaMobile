package hw2;

/**
 * Enum for selecting a suitable properties file
 */
public enum PropertiesType {
    NATIVE_PROPS("/src/main/resources/hw2/nativeTest.properties"),
    WEB_PROPS("/src/main/resources/hw2/webTest.properties");

    private String filePath;

    private PropertiesType(String filePath) {
        this.filePath = filePath;
    }

    public String getPath() {
        return filePath;
    }

}

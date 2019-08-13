package entities;

/**
 * Enum for selecting a suitable properties file
 */
public enum PropertiesType {
    NATIVE_PROPS("/src/main/resources/nativeTest.properties"),
    WEB_PROPS("/src/main/resources/webTest.properties");

    private String filePath;

    private PropertiesType(String filePath) {
        this.filePath = filePath;
    }

    public String getPath() {
        return filePath;
    }

}

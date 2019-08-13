package entities;

/**
 * Enum for selecting a suitable properties file
 */
public enum PropertiesType {
    NATIVE_ANDROID_PROPS("/src/main/resources/nativeAndroidTest.properties"),
    NATIVE_IOS_PROPS("/src/main/resources/nativeIOSTest.properties"),
    WEB_ANDROID_PROPS("/src/main/resources/webAndroidTest.properties"),
    WEB_IOS_PROPS("/src/main/resources/webIOSTest.properties");

    private String filePath;

    private PropertiesType(String filePath) {
        this.filePath = filePath;
    }

    public String getPath() {
        return filePath;
    }

}

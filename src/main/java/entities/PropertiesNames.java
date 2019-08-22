package entities;

public enum PropertiesNames {
    AUT ("aut"),
    APP_PACKAGE("appPackage"),
    APP_ACTIVITY("appActivity"),
    PLATFORM("platform"),
    DRIVER_URL("driverUrl"),
    AUTOMATION("automation"),
    INSTALLATION_URL("installationURL"),
    SUT("sut"),
    MAIN_PAGE_TITLE("mainPageTitle"),
    UDID("udid"),
    CLOUD_TOKEN("CLOUD_TOKEN");

    private String name;

    private PropertiesNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

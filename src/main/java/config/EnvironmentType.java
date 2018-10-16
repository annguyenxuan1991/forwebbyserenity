package config;

public enum EnvironmentType {

    SANDBOX("src/test/resources/environments/sandbox.yml"),
    DEV("src/test/resources/environments/dev.yml"),
    SIT("src/test/resources/environments/sit.yml"),
    UAT("src/test/resources/environments/uat.yml");

    private final String url;

    EnvironmentType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}

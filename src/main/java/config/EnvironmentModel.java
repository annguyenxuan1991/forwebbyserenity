package config;

import lombok.Data;

@Data
public class EnvironmentModel {
    private String baseWebUrl;

    private String baseUrl;

    private String mockUrl;

    private String port;

    private String version;

    private String baseUrlAccessToken;

    private String baseUrlGetOTP;

    private String authorization;

    private String clientIdNoneDevice;

    private String clientSecretNoneDevice;

    private String clientIdTrustedDevice;

    private String clientSecretTrustedDevice;

    private String redisHost;
}

package config;

import lombok.Data;
import lombok.NonNull;

@Data
public class ApplicationModel {
    @NonNull
    private String activeEnvironment;

    @NonNull
    private String reportDir;

    @NonNull
    private String screenshotsDir;
}

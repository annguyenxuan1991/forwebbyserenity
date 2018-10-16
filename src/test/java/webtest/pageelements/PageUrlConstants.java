package webtest.pageelements;

import config.EnvironmentHandler;
import config.EnvironmentModel;

public class PageUrlConstants {
    private PageUrlConstants() {
    }

    /**
     * Define your page url in here
     */
    private static final EnvironmentModel environmentModel = EnvironmentHandler.getInstance().getEnvironment();
    public static final String LANDING_PAGE_URL = environmentModel.getBaseWebUrl();
}

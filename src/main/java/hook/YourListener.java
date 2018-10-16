package hook;

import config.EnvironmentHandler;
import config.EnvironmentType;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.StepFailure;
import org.apache.log4j.Logger;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class YourListener extends SerenityCustomListener {
    private static final Logger LOGGER = Logger.getLogger(YourListener.class);

    /**
     * Setup the environment in here
     */
    @Override
    public void testStarted(String s, String s1) {
        LOGGER.info("STARTED THE TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + s);
        EnvironmentHandler.getInstance(EnvironmentType.DEV);
        deleteCookieAndMaximizeBrowser();
    }


    /**
     * Test outcome after running the test in here
     */
    @Override
    public void testFinished(TestOutcome testOutcome) {
        LOGGER.info("END THE TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Add screenshot for failed tests in here
     */
    @Override
    public void stepFailed(StepFailure stepFailure) {
    }

    private void deleteCookieAndMaximizeBrowser() {
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
    }
}

package helpers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

/**
 * This class solves common issues with WebElement.
 */
public class WebElementHelper {
    private static final Logger LOGGER = Logger.getLogger(WebElementHelper.class);

    private WebElementHelper() {
    }

    public static void sendKeysOneByOne(final By locator, final String text) {
        sendKeysOneByOne(getDriver().findElement(locator), text);
    }

    public static void sendKeysOneByOne(final WebElement element, final String text) {
        Actions actions = new Actions(getDriver());
        final int contentLength = element.getText().length();
        actions.moveToElement(element).click().perform();
        actions.moveToElement(element)
                .sendKeys(Keys.chord(Keys.END, StringUtils.repeat(String.valueOf(Keys.BACK_SPACE), contentLength), text))
                .perform();
        WebElement parent = findElement(By.xpath(".."));
        parent.click();
        parent.sendKeys(Keys.TAB);
    }

    public static void sendKeysAndCheck(final By locator, final String text) {
        sendKeysAndCheck(waitAndGetElement(locator, Constants.RENDER_ELEMENT_TIMEOUT), text);
    }


    public static boolean sendKeysAndCheck(final WebElement element, final String text) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(Constants.RENDER_ELEMENT_TIMEOUT, TimeUnit.SECONDS)
                .withMessage("Wait out while trying to input text " + text);
        return wait.until((Function<WebDriver, Boolean>) driver -> {
            element.clear();
            element.sendKeys(text);
            JavaScriptExecutorHelper.blur(element);
            if (text.equalsIgnoreCase(element.getAttribute("value"))) {
                return true;
            }
            LOGGER.info("Expected text='" + text + "' but the value is '" + element.getAttribute("value")
                    + "'. Retry to fill text");
            return false;
        });
    }

    /**
     * Some input need a blur and tab event to update the value
     */
    public static void sendKeysAndBlur(final WebElement element, final String value) {
        element.clear();
        sendKeysAndBlurNotClear(element, value);
    }

    public static void sendKeysAndBlurNotClear(final WebElement element, final String value) {
        element.sendKeys(value);
        JavaScriptExecutorHelper.blur(element);
        element.sendKeys(Keys.TAB);
    }

    public static void sendKeysAndBlur(final By locator, final String value) {
        final WebElement inputElement = waitAndGetElement(locator, Constants.RENDER_ELEMENT_TIMEOUT);
        sendKeysAndBlur(inputElement, value);
    }

    public static WebElement waitAndGetElement(final By locator, final long timeout) {
        waitForPresenceOfElementLocated(locator, timeout);
        return findElement(locator);
    }

    public static WebElement waitAndGetElement(final By locator) {
        return waitAndGetElement(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    /**
     * Some elements are never clickable. We can use {@link #waitAndGetElement(By)} then click().
     */
    public static void waitAndClick(final By locator) {
        waitAndClick(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static void waitAndClick(final By locator, long timeout) {
        waitForElementClickable(locator, timeout);
        findElement(locator).click();
    }

    public static boolean waitAndCheckClickable(final By locator) {
        return waitAndCheckClickable(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static boolean waitAndCheckClickable(final By locator, long timeout) {
        return waitForElementClickable(locator, timeout).isEnabled();
    }

    public static WebElement waitForElementClickable(final By locator, long timeout) {
        return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void clickUntilOK(final By locator, long timeout, long poolingTime,
                                    final Predicate<WebDriver> condition) {
        FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(poolingTime, TimeUnit.SECONDS).withMessage("Failed to retry click.")
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        wait.until((Function<WebDriver, Boolean>) driver -> {
            if (condition.apply(driver)) {
                return true;
            }
            try {
                findElement(locator).click();
            } catch (WebDriverException e) {
                LOGGER.error("Cannot click the element because it disappears. Continue test.", e);
            }
            return false;
        });
    }

    public static void waitForElementExist(final By locator) {
        waitForPresenceOfElementLocated(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static void waitForAnyElementExist(final By... locators) {
        waitForAnyElementExist(Constants.LOADING_TIMEOUT, locators);
    }

    public static boolean waitForAnyElementExist(final long timeout, final By... locators) {
        FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class);
        return wait.until((Function<WebDriver, Boolean>) driver -> {
            for (By locator : locators) {
                if (!findElements(locator).isEmpty()) {
                    return true;
                }
            }
            return false;
        });
    }

    public static void waitForInvisibilityOfElementLocated(final By locator) {
        waitForInvisibilityOfElementLocated(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static void waitForInvisibilityOfElementLocated(final By locator, final long timeout) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitAndSendKey(final By locator, String key) {
        waitForElementClickable(locator, Constants.RENDER_ELEMENT_TIMEOUT).sendKeys(key);
    }

    public static WebElement waitForPresenceOfElementLocated(final By locator) {
        return waitForPresenceOfElementLocated(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static WebElement waitForPresenceOfElementLocated(final By locator, final long timeout) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForPresenceOfElementsLocated(final By locator) {
        return waitForPresenceOfElementsLocated(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static List<WebElement> waitForPresenceOfElementsLocated(final By locator, final long timeout) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForVisibilityOfElementLocated(final By locator) {
        return waitForVisibilityOfElementLocated(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static WebElement waitForVisibilityOfElementLocated(final By locator, final long timeout) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Try to find the locator until it is not stale on screen. This implies that the element exists on screen.
     */
    public static void waitForElementNotStale(final By locator, final long timeout) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
        wait.until((Function<WebDriver, Boolean>)
                driver -> findElement(locator).isEnabled());
    }

    /**
     * Wait until the attribute contains the expected value
     */
    public static void waitForElementAttributeContains(final By locator, final long timeout, final String attribute,
                                                       final String expectedContainedValue) {
        waitForElementAttributeContainsOneOf(locator, timeout, attribute, expectedContainedValue);
    }

    /**
     * Wait until the attribute contains one of the expected values
     */
    public static void waitForElementAttributeContainsOneOf(final By locator, final long timeout,
                                                            final String attribute, final String... expectedContainedValues) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class);
        wait.until((Function<WebDriver, Boolean>) driver -> {
            final String attributeValue = findElement(locator).getAttribute(attribute);
            for (String expectedContainedValue : expectedContainedValues) {
                if (attributeValue.contains(expectedContainedValue)) {
                    return true;
                }
            }
            return false;
        });
    }

    public static WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static WebElement waitForElementVisible(By locator, long timeout) {
        return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementStoppedMoving(final By locator, long timeout) {
        new WebDriverWait(getDriver(), timeout).pollingEvery(1, TimeUnit.SECONDS)
                .until(new Function<WebDriver, Boolean>() {
                    Point position;

                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            final WebElement element = findElement(locator);
                            if (position != null && element.getLocation().equals(position)) {
                                return true;
                            }
                            position = element.getLocation();
                            return false;
                        } catch (StaleElementReferenceException e) {
                            LOGGER.error("Stale Element Reference Exception Cause: ", e);
                            return false;
                        }
                    }
                });
    }

    /**
     * This method allows empty list.
     */
    public static List<String> getListText(final By locator) {
        List<String> result = new ArrayList<>();
        for (WebElement element : findElements(locator)) {
            result.add(element.getText());
        }
        return result;
    }

    public static String waitAndGetText(final By locator) {
        return waitAndGetText(locator, Constants.RENDER_ELEMENT_TIMEOUT);
    }

    public static String waitAndGetText(final By locator, final long timeout) {
        return waitAndGetElement(locator, timeout).getText();
    }

    public static void clearAndSetText(final By locator, final String text) {
        WebElement webElement = waitForElementClickable(locator, Constants.RENDER_ELEMENT_TIMEOUT);
        webElement.clear();
        webElement.sendKeys(text);
    }

    public static String getAttributeValue(final By locator, final String attributeName) {
        waitForElementExist(locator);
        return findElement(locator).getAttribute(attributeName);
    }

    public static String getAttributeValue(final WebElement element, final String attributeName) {
        return element.getAttribute(attributeName);
    }

    public static boolean waitForURLContains(final String text) {
        final FluentWait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Constants.LOADING_TIMEOUT, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        return wait.until((Function<WebDriver, Boolean>) driver -> driver.getCurrentUrl().contains(text));
    }

    public static void tryToClickUntilDisappear(final By locator) {
        final FluentWait<WebDriver> wait =
                new FluentWait<>(getDriver()).withTimeout(Constants.RENDER_ELEMENT_TIMEOUT, TimeUnit.SECONDS);
        wait.until((Function<WebDriver, Boolean>) driver -> {
            while(findElement(locator).isDisplayed()) {
                findElement(locator).click();
            }
            return !findElement(locator).isDisplayed();
        });
    }

    public static WebElement findElement(final By locator) {
        return getDriver().findElement(locator);
    }

    private static List<WebElement> findElements(final By locator) {
        return getDriver().findElements(locator);
    }
}
package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class ActionHelper {

    private ActionHelper() {
    }

    public static void scrollToElement(WebElement element, int xOffSet, int yOffSet) {
        new Actions(getDriver()).moveToElement(element).perform();
        JavaScriptExecutorHelper.executeJavaScript(String.format("window.scrollBy(%s,%s)", xOffSet, yOffSet));
    }

    public static void moveToAndSendKeys(final By locator, final int positionIndexFrom0, final String value) {
        WebElement element = WebElementHelper.waitForPresenceOfElementsLocated(locator).get(positionIndexFrom0);
        new Actions(getDriver()).moveToElement(element).click().sendKeys(value).perform();
    }

    public static void moveToAndClearText(final By locator) {
        WebElement element = WebElementHelper.waitForPresenceOfElementLocated(locator);
        new Actions(getDriver()).moveToElement(element).click()
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE).perform();
    }

    public static void dragAndDrop(By dragLocator, By dropLocator) {
        dragAndDrop(getDriver().findElement(dragLocator), getDriver().findElement(dropLocator));
    }

    public static void dragAndDrop(WebElement dragElement, WebElement dropToElement) {
        Actions dragAndDropAction = new Actions(getDriver());
        dragAndDropAction.dragAndDrop(dragElement, dropToElement).build().perform();
    }

    public static void doubleClick(By locator) {
        doubleClick(getDriver().findElement(locator));
    }

    public static void doubleClick(WebElement element) {
        final Actions action = new Actions(getDriver());
        action.doubleClick(element);
        action.perform();
    }

    public static void moveElement(By locator, int moveX, int moveY) {
        moveElement(getDriver().findElement(locator), moveX, moveY);
    }

    public static void moveElement(WebElement webElement, int moveX, int moveY) {
        new Actions(getDriver()).dragAndDropBy(webElement, moveX, moveY).perform();
    }


    public static void focusElement(By locator) {
        focusElement(getDriver().findElement(locator));
    }

    public static void focusElement(WebElement webElement) {
        new Actions(getDriver()).moveToElement(webElement).perform();
    }

    public static void focusAndClickElement(By locator) {
        focusAndClickElement(getDriver().findElement(locator));
    }

    public static void focusAndClickElement(WebElement webElement) {
        new Actions(getDriver()).moveToElement(webElement).click().perform();
    }

    public static void hoverElement(By locator) {
        hoverElement(getDriver().findElement(locator));
    }

    public static void hoverElement(WebElement webElement) {
        new Actions(getDriver()).moveToElement(webElement).perform();
    }

    public static void sendKeysTab(final By locator) {
        sendKeysTab(getDriver().findElement(locator));
    }

    public static void sendKeysTab(final WebElement inputElement) {
        inputElement.sendKeys(Keys.TAB);
    }

    public static void sendKeysCtrC(final WebElement inputElement) {
        inputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.chord(Keys.CONTROL, "c"));
    }

    public static void sendKeysCtrV(final WebElement inputElement) {
        inputElement.clear();
        Actions action = new Actions(getDriver());
        inputElement.sendKeys(Keys.chord(Keys.CONTROL, "v"));
        action.build().perform();
    }

}

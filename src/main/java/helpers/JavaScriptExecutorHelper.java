package helpers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.assertj.core.api.AbstractObjectAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.LogExtends;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class helps to work with javascript. Eg. execute a javascript command, getId, click, blur
 */
public class JavaScriptExecutorHelper {
    private static final Logger LOGGER = Logger.getLogger(JavaScriptExecutorHelper.class);

    private JavaScriptExecutorHelper() {
    }

    public static String getValueJS(final String id) {
        if (StringUtils.isNotBlank(id)) {
            return String.valueOf(executeJavaScript("return document.getElementById('" + id + "').value;"));
        } else {
            return null;
        }
    }

    public static String getValueJS(final WebElement webElement) {
        final String id = getId(webElement);
        if (StringUtils.isNotBlank(id)) {
            return String.valueOf(executeJavaScript("return document.getElementById('" + id + "').value;"));
        } else {
            return null;
        }
    }

    public static void setValueJS(final WebElement webElement, String value) {
        final String id = getId(webElement);
        setValueJS(id, value);
    }

    public static void setValueJS(final String id, String value) {
        if (StringUtils.isNotBlank(id)) {
            String scriptText = XpathHelper.formatXpath("return document.getElementById('%s').value='%s';", value);
            executeJavaScript(String.format(scriptText, id, value));
        } else {
            throw new InvalidElementStateException("The element should have an id to be set with this method");
        }
    }

    public static void setValueAndTab(final WebElement webElement, String value) {
        setValueJS(webElement, value);
        webElement.sendKeys(Keys.TAB);
    }

    public static String getInnerHtmlForElementWithId(final String id) {
        return String
                .valueOf(executeJavaScript("return document.getElementById('" + id + "').innerHTML;"));
    }

    public static Object executeJavaScript(final String script) {
        AbstractObjectAssert<?, WebDriver> assertDriver = assertThat(getDriver());
        assertDriver.isNotNull();
        assertDriver.isInstanceOf(JavascriptExecutor.class);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        return javascriptExecutor.executeScript(script);

    }

    public static String getId(WebElement webElement) {
        assertThat(webElement).isNotNull();
        return webElement.getAttribute("id");
    }

    public static void blur(WebElement webElement) {
        getDriver().switchTo().window(getDriver().getWindowHandle());
        final String id = getId(webElement);
        if (StringUtils.isNotBlank(id)) {
            executeJavaScript("return document.getElementById('" + id + "').blur();");
        }
    }

    /**
     * Blur the input element to have fields update using EXT commands
     *
     * @param webElement
     */
    public static void extBlur(WebElement webElement) {
        final String id = getId(webElement);
        if (StringUtils.isNotBlank(id)) {
            executeJavaScript("Ext.getCmp('" + id + "').fireEvent('blur');");
        }
    }

    public static void click(WebElement webElement) {
        final String id = getId(webElement);
        click(id);
    }

    public static void click(String id) {
        LogExtends.info(String.format("Button id [%s]", id));
        if (StringUtils.isNotBlank(id)) {
            LogExtends.info(String.format("Click by JavaScript on button id [%s]", id));
            executeJavaScript("return document.getElementById('" + id + "').click();");
        }
    }

    public static void doubleClickByJS(final WebElement element) {
        try {
            new Actions(getDriver()).doubleClick(element).perform();
        } catch (WebDriverException ex) {
            LOGGER.warn("Element is not clickable. Try to click by Javascript.");
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].ondblclick()", element);
        }
    }

    public static void refreshIframe(final String iframeId) {
        executeJavaScript("document.getElementById('" + iframeId + "').src = document.getElementById('" + iframeId + "').src;");
    }

    public static void fireExtJsEvent(final String elementId, final String eventName, final String value) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        String script = "Ext.getCmp('%s').fireEvent('%s', %s);";
        javascriptExecutor.executeScript(String.format(script, elementId, eventName, value));
    }

    public static void scrollToElement(By locator) {
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        WebElement element = getDriver().findElement(locator);
        je.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElement(WebElement webElement) {
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public static void click(By locator) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click()", getDriver().findElement(locator));
    }

    public static void uploadFile(final String fileUploadInputId, final String filePath) {
        LOGGER.info(String.format("Upload file [%s]", filePath));
        JavaScriptExecutorHelper.executeJavaScript("document.getElementById('" + fileUploadInputId + "').style = 'display:block;';");
        getDriver().findElement(By.id(fileUploadInputId)).sendKeys(filePath);
    }
}

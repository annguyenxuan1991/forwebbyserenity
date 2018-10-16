package utils;

/**
 * This Utils to find PageObject by XPath
 */
public final class XPathUtils {

    private XPathUtils() {

    }

    /**
     * Send the text need to find in XPath
     * @param className the keyword to find
     * @return String to find by XPath
     */
    public static String getTagInputByClass(String className) {
        return ".//input[@class = '" + className + "']";
    }

    /**
     * Send the text need to find in XPath
     * @param className the keyword to find
     * @return String to find by XPath
     */
    public static String getTagDivByClass(String className) {
        return ".//div[@class = '" + className + "']";
    }

    /**
     * Send the text need to find in XPath
     * @param id the id of the tag
     * @return String to find by XPath
     */
    public static String getTagDivById(String id) {
        return ".//div[@id = '" + id + "']";
    }

    /**
     * Send the text need to find in XPath
     * @param className the keyword to find
     * @return String to find by XPath
     */
    public static String getTagButtonByClass(String className) {
        return ".//button[@class = '" + className + "']";
    }
}

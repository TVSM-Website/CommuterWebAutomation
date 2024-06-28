package Utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utilities
{
    public static Properties properties = new Properties();

    public static void clickElementUsingJS(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void scrollToView(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    //Used in @Scooter to change '+' to 'plus' and lower case conversion
    public static String normalizeString(String input) {
        // Normalize the string by converting to lower case and replacing '+' with 'plus'

        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ENGLISH)
                .replace("+", "plus")
                .replaceAll("[^a-z0-9]", " ")  // Replace non-alphanumeric characters with a space
                .replaceAll("\\s+", "")        // Remove all spaces
                .trim();                       // Trim leading/trailing spaces
    }

    public static WebElement refreshElement(WebElement element, WebDriver driver) {
        // Get the locator from the WebElement
        By locator = getLocatorFromElement(element);

        // Use the locator to find the element again and return it
        return driver.findElement(locator);
    }

    /**
     * Retrieves the locator used to find the WebElement.
     *
     * @param element The WebElement to extract the locator from.
     * @return The By locator used to find the WebElement.
     */
    private static By getLocatorFromElement(WebElement element) {
        String[] data = element.toString().split(" -> ")[1].replaceFirst("(?s)(.*)\\]", "$1" ).split(": ");
        String locatorType = data[0];
        String locatorValue = data[1];

        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "className":
                return By.className(locatorValue);
            case "tagName":
                return By.tagName(locatorValue);
            case "linkText":
                return By.linkText(locatorValue);
            case "partialLinkText":
                return By.partialLinkText(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "cssSelector":
                return By.cssSelector(locatorValue);
            default:
                throw new IllegalArgumentException("Cannot determine locator type: " + locatorType);
        }
    }


}


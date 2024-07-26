package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Utils.WebDriverManager.driver;

public class Utilities
{
    public static Properties properties = new Properties();

    public static void scrollToElement(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static void scrollByPixels(int pixels)
    {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, "+pixels+");");
    }

    public static String getVehicleUrl(String vehicle, String env) throws IOException
    {
        String propertiesFilePath;

        if (env.equalsIgnoreCase("UAT")) {
            propertiesFilePath = "src/test/Resources/BrandPageUrlUAT.properties";
        } else if (env.equalsIgnoreCase("PROD")) {
            propertiesFilePath = "src/test/Resources/BrandPageUrlProd.properties";
        } else {
            throw new IllegalArgumentException("Invalid environment: " + env);
        }

        // Load the properties file
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the URL for the given vehicle
        String selectedVehicleUrl = properties.getProperty(vehicle);

        if (selectedVehicleUrl == null) {
            throw new IllegalArgumentException("No URL found for vehicle: " + vehicle);
        }

        return selectedVehicleUrl;
    }
    public static String getUrl(String environment) {
        if ("UAT".equalsIgnoreCase(environment)) {
            return "https://uat-www.tvsmotor.net/";
        } else if ("PROD".equalsIgnoreCase(environment)) {
            return "https://www.tvsmotor.com/";
        } else {
            throw new IllegalArgumentException("Invalid environment: " + environment);
        }
    }

    public static void verifyUrl(WebDriver driver, String environment) {
        String currentUrl = driver.getCurrentUrl();
        if ("UAT".equalsIgnoreCase(environment)) {
            if (!currentUrl.contains("uat")) {
                throw new AssertionError("URL does not contain 'uat'");
            }
        } else if ("PROD".equalsIgnoreCase(environment)) {
            if (currentUrl.contains("uat")) {
                throw new AssertionError("URL contains 'uat'");
            }
        }
    }

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

        String normalized =  Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ENGLISH)
                .replace("+", " plus")
                .replaceAll("[^a-z0-9]", " ")  // Replace non-alphanumeric characters with a space
                .replaceAll("\\s+", " ")        // Remove all spaces
                .trim();                       // Trim leading/trailing spaces
        // Perform case-insensitive replacement of "orissa" with "odisha"
        Pattern pattern = Pattern.compile("\\b(orissa)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(normalized);
        normalized = matcher.replaceAll("odisha");
        System.out.println(normalized + "here at utilities");
        normalized.replaceAll("\\s+", "");
        return normalized;
    }

    public static String normalizeStateName(String stateName) {
        // Normalize the state name by converting to lower case, replacing variations of "orissa" with "odisha" for TVS Sport
        String normalizedStateName = normalizeString(stateName);
        return normalizedStateName;
    }
    public static WebElement refreshElement(WebElement element, WebDriver driver) {
        // Get the locator from the WebElement
        By locator = getLocatorFromElement(element);

        // Use the locator to find the element again and return it
        return driver.findElement(locator);
    }

    
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

package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;

import static Utils.WebDriverManager.getDriver;


public class Utilities
{

    public static Properties properties = new Properties();

    public static void scrollToElement(WebElement element)
    {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static void scrollByPixels(int pixels)
    {
        ((JavascriptExecutor)getDriver()).executeScript("window.scrollBy(0, "+pixels+");");
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
        }
        else if ("TestRide_UAT".equalsIgnoreCase(environment)) {
            return "https://uat-www.tvsmotor.net/book-a-ride";
        }
        else if ("TestRide_PROD".equalsIgnoreCase(environment)) {
            return "https://www.tvsmotor.com/book-a-ride";
        }
        else if("TestRideCamp_UAT".equalsIgnoreCase(environment))
        {
            return "https://www.tvsmotor.com/book-a-ride-campaign";
        }
        else {
            throw new IllegalArgumentException("Invalid environment: " + environment);
        }
    }

    public static void verifyUrl(WebDriver driver, String environment)
    {
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

    public static boolean HandleAlert(WebDriver driver,String password)
    {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(password);
            alert.accept();
            System.out.println("Alert handled successfully.");
            return true;
        } catch (NoAlertPresentException e)
        {
            System.out.println("No alert was present.");
            return false;
        }

    }

    public static String getProductUrl(String vehicle) throws IOException {
        String propertiesFilePath = "src/test/Resources/ProductsUrls.properties";

        // Load the properties file
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: " + propertiesFilePath, e);
        }

        if (!vehicle.contains("uat") && !vehicle.contains("prod")) {
            throw new IllegalArgumentException("Invalid environment in vehicle input: " + vehicle);
        }

        String selectedVehicleUrl = properties.getProperty(vehicle);
        if (selectedVehicleUrl == null) {
            throw new IllegalArgumentException("No URL found for key: " + vehicle);
        }

        return selectedVehicleUrl;
    }


    public static class VariantMapper
    {

        // Map to convert UI variants to Excel variant codes
        private static final Map<String, String> VARIANT_MAP = Map.of(
                "BASE", "1CH BASE",   // Map UI "BASE" to Excel "1CH BASE"
                "MID", "2CH MID",     // Map UI "MID" to Excel "2CH MID"
                "TOP", "2CH TOP"      // Map UI "TOP" to Excel "2CH TOP"
        );

        // Parse the UI model name to extract variant and color
        public static String[] parseVariantAndColor(String uiModelName) {
            String[] parts = uiModelName.split("–"); // Split based on en dash (U+2013)
            if (parts.length < 2) return new String[] {null, null};

            String variantAndColor = parts[1].trim().toLowerCase(); // e.g., "base lightning black"
            String[] variantColorParts = variantAndColor.split(" ", 2); // Split into variant and color
            if (variantColorParts.length < 2) return new String[] {null, null};

            String uiVariant = variantColorParts[0].trim().toLowerCase(); // e.g., "base"
            String uiColor = variantColorParts[1].trim().toLowerCase();   // e.g., "lightning black"

            // Convert the variant to the Excel format
            String mappedVariant = VARIANT_MAP.getOrDefault(uiVariant, uiVariant); // Use the map or fallback

            return new String[] {capitalizeFirstLetter(mappedVariant), capitalizeFirstLetter(uiColor)};
        }

        // Capitalize the first letter of each word in a string
        public static String capitalizeFirstLetter(String text)
        {
            if (text == null || text.isEmpty()) return text;
            String[] words = text.split(" ");
            StringBuilder capitalizedText = new StringBuilder();
            for (String word : words) {
                capitalizedText.append(word.substring(0, 1).toUpperCase());
                capitalizedText.append(word.substring(1)).append(" ");
            }
            return capitalizedText.toString().trim();
        }
    }

    public static String mapVariantFromBase(String variantKeyword) {
        return switch (variantKeyword.toUpperCase()) {
            case "BASE" -> "1CH BASE";
            case "MID" -> "2CH MID";
            case "TOP" -> "2CH TOP";
            default -> variantKeyword; // fallback
        };
    }
}

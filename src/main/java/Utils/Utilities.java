package Utils;

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

        // Determine the correct properties file based on the environment
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

}

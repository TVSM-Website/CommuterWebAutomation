package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    private static final Map<String, String> stateMapping;

    static {
        stateMapping = new HashMap<>();
        stateMapping.put("Andaman and Nicobar Islands", "Andaman");
        stateMapping.put("Andhra Pradesh", "Andhra pradesh");
        stateMapping.put("Arunachal Pradesh", "Northeast - Arunachal Pradesh");
        stateMapping.put("Dadra and Nagar Haveli", "Silvasa");
        stateMapping.put("Jammu and Kashmir", "Jammu & Kashmir");
        stateMapping.put("Madhya Pradesh", "Madhya pradesh");
        stateMapping.put("Manipur", "Northeast - Manipur");
        stateMapping.put("Mizoram", "Northeast - Mizoram");
        stateMapping.put("Nagaland", "Northeast - Nagaland");
        stateMapping.put("Puducherry", "Pondicherry");
        stateMapping.put("Tamil Nadu", "Tamilnadu");
        stateMapping.put("Tripura", "Northeast -Tripura");
        stateMapping.put("Uttar Pradesh", "Uttar pradesh");

    }

    public static String normalizeStateName(String state) {
        return stateMapping.getOrDefault(state, state);
    }

}

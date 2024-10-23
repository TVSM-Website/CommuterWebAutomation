package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private WebDriverManager() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    private static void initializeDriver() {
        // You can switch between Chrome or Edge drivers based on requirements
        //WebDriver webDriver = new EdgeDriver(); // For Edge browser
        WebDriver webDriver = new ChromeDriver(); // For Chrome browser
        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit(); // Close the browser session
            driver.remove();     // Remove the WebDriver instance from ThreadLocal
        }
    }
}

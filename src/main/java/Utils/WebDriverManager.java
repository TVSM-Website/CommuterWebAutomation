package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager
{
        static WebDriver driver;

        private WebDriverManager() {
            // Private constructor to prevent instantiation
        }

        public static WebDriver getDriver() {
            if (driver == null) {
                initializeDriver();
            }
            return driver;
        }

        private static void initializeDriver() {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        public static void quitDriver() {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }



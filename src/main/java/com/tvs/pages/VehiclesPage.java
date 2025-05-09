package com.tvs.pages;

import com.tvs.pages.PriceSectionPages.PriceSectionPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.ExplicitWait;
import Utils.Utilities;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VehiclesPage {
    private static final Logger log = LogManager.getLogger();
    WebDriver driver;
    PriceSectionPage priceSectionPage;
    String originalWindowHandle;
    WebElement selectlanguagePopUp;

    public VehiclesPage(WebDriver driver) {
        this.driver = driver;
        this.originalWindowHandle = driver.getWindowHandle();// original window handle
        priceSectionPage = new PriceSectionPage(driver);
        selectlanguagePopUp=priceSectionPage.selectlanguagePopUp;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//li[@data-id='#set1']/a")
    private WebElement scootersTab;

    @FindBy(xpath = "//li[@data-id='#set2']/a")
    private WebElement motorCyclesTab;

    @FindBy(xpath = "//li[@data-id='#set3']/a")
    private WebElement mopedTab;

    @FindBy(xpath = "//div[@data-id='#set2']/descendant::p[@class='name']")
    private List<WebElement> motorcycleList;

    @FindBy(xpath = "//div[@data-id='#set1']/descendant::p[@class='name']")
    private List<WebElement> scootersList;

    @FindBy(xpath = "//div[@data-id='#set3']/descendant::p[@class='name']")
    private List<WebElement> mopedList;

    @FindBy (xpath = "//select[@id= 'stateDrp']")
    private WebElement stateDropdown;

    public void clickstateDropdown() {
        ExplicitWait.waitForElementToBeClickable(driver, stateDropdown, 2); stateDropdown.click();
    }
    public void clickScooterTab() {
        ExplicitWait.waitForElementToBeClickable(driver, scootersTab, 2); scootersTab.click();
    }

    public void clickMotorCycleTab() {
        ExplicitWait.waitForElementToBeClickable(driver, motorCyclesTab, 2); motorCyclesTab.click();
    }

    public void clickMopedTab() {
        ExplicitWait.waitForElementToBeClickable(driver, mopedTab, 2); mopedTab.click();
    }

    public List<WebElement> getMotorcyclesList() {
        return motorcycleList;
    }

    public List<WebElement> getScootersList() {
        return scootersList;
    }

    public List<WebElement> getMopedsList() {
        return mopedList;
    }

    public String getVehicleName(WebElement vehicleElement) {
        return vehicleElement.getText();
    }

    public List<WebElement> getStateDropdownOptions() {
        stateDropdown.click();
//      Thread.sleep(2000);
        Select select = new Select(stateDropdown);
        return select.getOptions();
    }

    public List<WebElement> getVehicleList(String vehicleType) {
        String dataID = driver.findElement(By.xpath("//li[contains(@class,'filter')]/a[text()='"+vehicleType+"']/..")).getAttribute("data-id");
        return driver.findElements(By.xpath("//div[@data-id='"+dataID+"']/descendant::p[@class='name']"));
    }

    public boolean isKnowMoreLinkAvailable(String vehicleName) {
        try {
            WebElement knowMoreLink = driver.findElement(By.xpath("//p[text()='"+vehicleName+"']/../../../descendant::a"));
            return knowMoreLink.isDisplayed();
        } catch (Exception e) {
            log.error("No 'Know More' link found for vehicle: " + vehicleName, e);
            return false;
        }

    }

    public void clickKnowMoreLink(String vehicleName) {
        WebElement knowMoreLink = driver.findElement(By.xpath("//p[text()='"+vehicleName+"']/../../../descendant::a"));
        knowMoreLink.click();
    }

    public String getExpectedUrlSubstring(String vehicleName) {
        // Normalize the vehicle name first
        String normalizedVehicleName = Utilities.normalizeString(vehicleName);

        // Define a mapping of full vehicle names to expected URL substrings
        Map<String, String> vehicleToUrlMap = new HashMap<>();
        vehicleToUrlMap.put("tvs zest 110", "tvs zest"); // Example exception
        vehicleToUrlMap.put("tvs scooty pep plus", "tvs scootypep");
        vehicleToUrlMap.put("tvs apache rtr series","tvs apache rtrseries");
        // Add other mappings as needed

        // Check if there's a specific mapping for the normalized vehicle name
        if (vehicleToUrlMap.containsKey(normalizedVehicleName)) {
            return vehicleToUrlMap.get(normalizedVehicleName);
        }

        // If no specific mapping, use the normalized vehicle name itself
        return normalizedVehicleName;
    }

    public void handlePopups() throws InterruptedException {
        //select language pop up - selecting language as 'English'
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement languagePopup =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Please Select Language']")));
            if (languagePopup.isDisplayed()) {

                By languageSelector = priceSectionPage.getLanguageSelector("English");

                WebElement languageElement = driver.findElement(languageSelector);

                ExplicitWait.waitForElementToBeClickable(driver, languageElement, 10);

                languageElement.click();

            }
//            if (languagePopup.isDisplayed()) {
//                WebElement languageSelector = driver.findElement(By.xpath("//button[text()='English']"));
//                languageSelector.click();
//            }
        } catch (Exception e) {
            log.info("Language selection pop-up not found or handled.");
        }
        Thread.sleep(5000);
        //request a call back pop up - closing the pop up
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Request A Call Back')]")));
            WebElement requestCallbackPopup = driver.findElement(By.xpath("//*[contains(text(),'Request A Call Back')]"));
            if (requestCallbackPopup.isDisplayed()) {
                WebElement closeButton = driver.findElement(By.xpath("//button[@class='close evg-close evg-btn-dismissal']"));
                closeButton.click();
            }
        } catch (Exception ignored) {
        }
    }

    public void ClickOurProducts() {
        WebElement ourProductsLink = driver.findElement(By.xpath("//a[text()='Our Products']"));
        ourProductsLink.click();
    }

    public boolean switchToNewTabIfOpened() {
        Set<String> allWindowHandles = driver.getWindowHandles();
        if (allWindowHandles.size() > 1) {
            for (String windowHandle : allWindowHandles) {
                if (!windowHandle.equals(originalWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    return true;
                }
            }
        }
        return false;
    }

    public void switchBackToOriginalTab() {
        driver.close();
        driver.switchTo().window(originalWindowHandle);
    }
}


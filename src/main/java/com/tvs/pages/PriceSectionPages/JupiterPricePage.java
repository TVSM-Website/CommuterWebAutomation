package com.tvs.pages.PriceSectionPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JupiterPricePage
{
    WebDriver driver;
    WebDriverWait wait;

    public JupiterPricePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath = "//div[@class='location-wrap']")
    private WebElement locationPopUp;

    @FindBy(xpath = "//div[@class='location-wrap']/a")
    private WebElement closeButton;

    @FindBy(xpath = "//li[@class='nav-item']/a[@href='#price-section']")
    private WebElement priceButton;

    @FindBy(xpath = "//div[@class='selectCitySection']/select[@id='pstate']")
    private WebElement stateDropdown;

    @FindBy(xpath = "//li[@data-id='priceid2']")
    private WebElement OnRoadPriceTab;

    @FindBy(xpath = "//div[@class='ori-absolute stackViewCloseIcon']")
    private WebElement botCloseButton;

    public By states= By.xpath("//select[@id='pstate']/option");

    public void closeLocationPopUp()
    {
        try {
            if (locationPopUp.isDisplayed())
            {
                System.out.println("Location Popup is displayed.");
                try {
                    // Try clicking the close button
                    closeButton.click();
                    System.out.println("Popup closed using direct click.");
                } catch (Exception e) {
                    // If direct click fails, use JavaScript click
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", closeButton);
                    System.out.println("Popup closed using JavaScript click.");
                }
                wait.until(ExpectedConditions.invisibilityOf(locationPopUp));
                System.out.println("Popup is now invisible.");
            } else {
                System.out.println("Popup did not appear. No action needed.");
            }
        } catch (Exception e) {
            System.out.println("Popup not found or already closed: " + e.getMessage());
        }
    }

    public void clickPriceButton()
    {
        wait.until(ExpectedConditions.elementToBeClickable(priceButton));
        priceButton.click();
    }

    public void ClickStateDropdown()
    {
        wait.until(ExpectedConditions.elementToBeClickable(stateDropdown));
        stateDropdown.click();
    }

    public void ClickOnRoadPriceTab()
    {
        wait.until(ExpectedConditions.elementToBeClickable(OnRoadPriceTab));
        OnRoadPriceTab.click();
    }

    public void closeBotPopUp()
    {
        wait.until(ExpectedConditions.elementToBeClickable(botCloseButton));
        botCloseButton.click();
    }
}

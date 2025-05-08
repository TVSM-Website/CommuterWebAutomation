package com.tvs.pages.PriceSectionPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Utils.ExplicitWait.*;

public class RaiderPricePage
{

    WebDriver driver;
    public RaiderPricePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='cookie_but']/a")
    public WebElement AcceptCookie;

    @FindBy(xpath = "//div[@class='filter-option']")  //
    public WebElement stateDropdown;

    @FindBy(xpath = "(//div[@class='tvs-state-dropdown'])[2]")
    public WebElement stateDropdownRaider;

    @FindBy(xpath = "//div[@id='pricestate']")
    public WebElement roninStateDropdown;

    @FindBy(xpath = "//section[@id='price']")
    public WebElement raiderPriceSection;

    @FindBy(css = ".right.carousel-control")
    public WebElement rightArrow;

    public By enquiryPopUp=By.xpath("//div[@class='popup-details']/button");

    public By states=By.xpath("(//div[contains(@class, 'tvs-state-dropdown-list-item')])[position() > 34]");

    public By statesRaider =By.xpath("//div[@id='bs-select-1']/ul/li");

    @FindBy(xpath = "//div[contains(@class, 'langCont')]")
    public WebElement selectlanguagePopUp;


    @FindBy(xpath = "//div[@class='evg-brand-img']")
    public  WebElement evenBrandImage;

    @FindBy(xpath = "//button[contains(@class, 'evg-btn-dismissal')]")
    public WebElement bookingPopUp;

    @FindBy(xpath = "(//li[@id='brand-page-orp']//a)[6]")
    private WebElement brandPageOrp;

    @FindBy(xpath = "//a[@href='#ex-showroom']")
    public WebElement brandPageExshooroom;

    @FindBy(xpath = "//div[@class='item active']//li[@id='brand-page-orp']")
    private WebElement brandPageOrpRaider;

    @FindBy(xpath = "//div[@id='pricestate']")
    public WebElement tabContent;

    //public final By selectedVariant = By.cssSelector("div[class='item active'] h4");

    @FindBy(xpath="//button[@class='btn dropdown-toggle btn-default' and @data-id='variant']")
    public WebElement apacheVariantDropdown;

    @FindBy(xpath="(//button[contains(@class, 'btn dropdown')])[2]")
    public WebElement apacheStateDropdown;

    public By getLanguageSelector(String langName)
    {

        By selectlanguagePopUp=By.xpath("//div[@class='selectLang']/a[@data-name='"+langName+"']");
        return selectlanguagePopUp;
    }

    public void ClickAcceptCookies()
    {
        AcceptCookie.click();
    }

    public void ClickStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdown,15);
        StateDrop.click();
    }
    public void ClickApacheStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,apacheStateDropdown,15);
        StateDrop.click();
    }
    public void clickRoninStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,roninStateDropdown,15);
        StateDrop.click();
    }
    public void clickRaiderStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdownRaider,15);
        StateDrop.click();
    }
    public void ClickOnRoadPrice()
    {
//        WebElement brandOrp=waitForElementToBeClickable(driver,brandPageOrp,10);
//        brandOrp.click();
        WebElement onRoadTab = driver.findElement(By.xpath("(//li[@id='brand-page-orp']//a)[6]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", onRoadTab);
    }

    public void ClickOnExshowRoomPrice()
    {
        WebElement exshowroom=waitForElementToBeClickable(driver,brandPageExshooroom,10);
        exshowroom.click();
    }

    public void ClickOnRoadPriceRaider()
    {
        WebElement brandOrp=waitForElementToBeClickable(driver,brandPageOrpRaider,10);
        brandOrp.click();
    }
    public void clickDismiss()
    {
        bookingPopUp.click();
    }
    public boolean isDisplayed()
    {
        return evenBrandImage.isDisplayed();
    }

    public void clickRightArrow()
    {
        rightArrow.click();
    }

    public void closePopUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Wait for popup to appear - if not present, TimeoutException will be caught
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("evg-propensity-popup")));

            WebElement closeBtn = popup.findElement(By.cssSelector(".close-popup"));

            try {
                closeBtn.click();
            } catch (Exception e) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", closeBtn);
            }

            // Wait for popup to disappear
            wait.until(ExpectedConditions.invisibilityOf(popup));

        } catch (TimeoutException e) {
            // Popup did not appear - safe to skip
            System.out.println("Popup not found. Continuing without closing it.");
        }

    }
}

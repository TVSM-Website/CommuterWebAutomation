package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static Utils.ExplicitWait.waitForElementToBeClickable;

public class PriceSectionPage
{

    WebDriver driver;
    public PriceSectionPage(WebDriver driver)
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

    //public By states=By.xpath("//div[@id='bs-select-1']/ul/li/a/span");
    //public By states=By.xpath("//div[@class='form-group stateDrpDwn'][1]/div//div[@class='inner open']/ul/li/a/span");
    public By states = By.xpath("(//div[@id='pricestate-drop']//ul/li[@value and @data-state-name] | //select[@id='state']/option[@value and normalize-space()] | //div[contains(@class, 'dropdown-menu')]//ul/li[normalize-space()])[position() <= 34]");

    //public By states=By.xpath("(//div[contains(@class, 'tvs-state-dropdown-list-item')])[position() > 34]");

    public By apacheStates=By.xpath("(//div[@class='form-group stateDrpDwn'])[2]//div[@class='dropdown-menu open']/ul/li/a/span[@class='text' and not(contains(text(), 'Choose State'))]");
    public By statesRaider =By.xpath("//div[@id='bs-select-1']/ul/li");

    @FindBy(xpath = "//div[contains(@class, 'langCont')]")
    public WebElement selectlanguagePopUp;


    @FindBy(xpath = "//div[@class='evg-brand-img']")
    public  WebElement evenBrandImage;

    @FindBy(xpath = "//button[contains(@class, 'evg-btn-dismissal')]")
    public WebElement bookingPopUp;

    @FindBy(id = "brand-page-orp")
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

    public By ApacheVariants=By.xpath("(//div[@class='form-group stateDrpDwn']//ul[contains(@class, 'dropdown-menu inner')]/li[not(contains(@class, 'disabled')) and not(contains(@class, 'bs-title-option'))])[position() <= 6]/a/span[@class='text']");

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
        WebElement brandOrp=waitForElementToBeClickable(driver,brandPageOrp,10);
        brandOrp.click();
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


}

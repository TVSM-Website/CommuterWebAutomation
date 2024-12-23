package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static Utils.ExplicitWait.waitForElementToBeClickable;

public class TvsSportPricePage {
    WebDriver driver;

    public TvsSportPricePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@class, 'evg-btn-dismissal')]")
    public WebElement bookingPopUp;
    @FindBy(xpath = "//div[@class='evg-brand-img']")
    public WebElement evenBrandImage;
    @FindBy(xpath = "//div[@class='cookie_but']/a")
    public WebElement AcceptCookie;

    @FindBy(id = "tab-example-tab-onroad")
    public WebElement OnRoadPrice;


//    @FindBy(css = "div.language-popup.show")
//    public WebElement selectlanguagePopUp;

    public By selectlanguagePopUp = By.cssSelector("div.language-popup.show");


    public By states = By.xpath("//div[@class='dropdown-menu show']//a[@class='dropdown-item']");

    @FindBy(xpath = "//div[@class='dropdown']")
    public WebElement stateDropdown;

    public void ClickOnRoadPrice()
    {
        WebElement brandOrp = waitForElementToBeClickable(driver, OnRoadPrice, 10);
        brandOrp.click();
    }

    public void ClickStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdown,15);
        StateDrop.click();
    }
    public By getLanguageSelector(String langName)
    {

        return By.xpath("//div[@class='selectLang']/a[@data-name='"+langName+"']");
    }

    public void clickDismiss()
    {
        bookingPopUp.click();
    }
    public boolean isDisplayed()
    {
        return evenBrandImage.isDisplayed();
    }

    public void ClickAcceptCookies()
    {

        AcceptCookie.click();
    }
}

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

    @FindBy(xpath = "//div[@class='filter-option']")
    public WebElement stateDropdown;

    public By states=By.xpath("//div[@id='bs-select-1']/ul/li/a/span");
    //public WebElement states;

    @FindBy(id = "brand-page-orp")
    private WebElement brandPageOrp;

    public void ClickAcceptCookies()
    {
        AcceptCookie.click();
    }

    public void ClickStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdown,5);
        StateDrop.click();
    }
    public void ClickOnRoadPrice()
    {
        WebElement brandOrp=waitForElementToBeClickable(driver,brandPageOrp,5);
        brandOrp.click();
    }


}

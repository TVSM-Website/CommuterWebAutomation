package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static Utils.ExplicitWait.waitForElementToBeClickable;

public class ExshowRoomPricePage
{

    WebDriver driver;
    public ExshowRoomPricePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='filter-option']")
    public  WebElement stateDropdown;

    @FindBy(css = ".right.carousel-control")
    public WebElement rightArrow;

    @FindBy(xpath = "(//div[@class='filter-option-inner'])[3]")
    public WebElement stateDropdownRaider;

    public By states=By.xpath("(//div[@id='pricestate-drop']//ul/li[@value and @data-state-name] | //select[@id='state']/option[@value and normalize-space()] | //div[contains(@class, 'dropdown-menu')]//ul/li[normalize-space()])[position() <= 34]");
    public By statesRaider =By.xpath("//div[@id='bs-select-1']/ul/li");

    public void ClickStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdown,15);
        StateDrop.click();
    }

    public void clickRaiderStateDropdown()
    {
        WebElement StateDrop=waitForElementToBeClickable(driver,stateDropdownRaider,15);
        StateDrop.click();
    }
    public void clickRightArrow()
    {
        rightArrow.click();
    }
}

package com.tvs.pages.PriceSectionPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RoninPricePage
{
    WebDriver driver;
    public RoninPricePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
}

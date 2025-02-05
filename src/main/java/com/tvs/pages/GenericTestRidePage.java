package com.tvs.pages;

import Utils.ExplicitWait;
import Utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GenericTestRidePage
{


    WebDriver driver;
    public GenericTestRidePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }


    public By VehiclesList=By.xpath("//div[@id='mCSB_1_container']/ul/li");

    public By VehicleDropdown=By.xpath("//div[@class='vehicleSelectBox']");

    public By requestOTP=By.id("requestOTP");

    public void clickVehicleDropdown()
    {
        ExplicitWait.waitForElementToBeClickable(driver,driver.findElement(VehicleDropdown),5).click();
    }

    public void ClickRequestOtp()
    {
       driver.findElement(requestOTP).click();
    }
}

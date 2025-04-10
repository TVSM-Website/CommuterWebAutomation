package com.tvs.pages;

import Utils.Utilities;
import Utils.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static Utils.ExplicitWait.*;

public class PageSpeedPage {
    WebDriver driver;


    public PageSpeedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//input[@inputmode='url']")
    public WebElement Search_Url;

    @FindBy(xpath = "//button[@jsname='O2CIGd']")
    public WebElement Analyse_button;

    @FindBy(xpath = "(//c-wiz//a[contains(text(),'LCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[1]")
    public WebElement Mobile_LCP_Text;

    @FindBy(xpath = "(//c-wiz//a[contains(text(),'FCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[1]")
    public WebElement Mobile_FCP_Text;

    @FindBy(xpath = "(//a[contains(text(),'LCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[11]")
    public WebElement Desktop_LCP_Text;
    @FindBy(xpath = "(//a[contains(text(),'FCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[11]")
    public WebElement Desktop_FCP_Text;
    @FindBy(xpath = "(//a[contains(text(),'LCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[6]")
    public WebElement Alternate_Desktop_LCP_Text;
    @FindBy(xpath = "(//a[contains(text(),'FCP')]/../following::div//child::span[contains(@class,'Ykn2A')])[6]")
    public WebElement Alternate_Desktop_FCP_Text;


    @FindBy(xpath = "//button[@id='desktop_tab']")
    public WebElement Desktop_icon;

    public void ClickSearchUrl(String url) throws InterruptedException {
        waitForElementToBeClickable(driver, Search_Url, 10);
        Search_Url.click();
        Search_Url.clear();
        Search_Url.sendKeys(url);
        Analyse_button.click();
        waitUntilLoaderDisappears(driver);
        Thread.sleep(4000);
        waitForElementToBeClickable(driver,Mobile_LCP_Text,10);

    }

    public void ClickDesktop() throws InterruptedException {
        waitForElementToBeClickable(driver, Desktop_icon, 10);
        Desktop_icon.click();
        Thread.sleep(3000);
    }
}
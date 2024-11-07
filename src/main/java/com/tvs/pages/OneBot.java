package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import Utils.ExplicitWait;
import Utils.Utilities;
import Utils.WebDriverManager;

import java.util.List;

import static Utils.ExplicitWait.waitForElementToBeClickable;
public class OneBot {
    WebDriver driver;

    public OneBot(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@role= 'button']")
    public WebElement OneBotButton;

    @FindBy(xpath = "//div[text()= 'Chat with us']/following::div[@class='floatingItemIcon']")
    public WebElement ChatWithUs;

    @FindBy(xpath = "//div[@id='chatbotContentContainer']")
    public WebElement ChatbotContentContainer;

    @FindBy(xpath = "//div[@class='TextWithMediaBody-module_textWithMediaContainer__klUH8 TextWithMediaBody-module_ie10upTextWithMediaContainer__18kcd']")
    public WebElement ChatbotWelcomeText;

    @FindBy(xpath = "//span[text()='English']")
    public WebElement EnglishLanguage;

    @FindBy(xpath = "//span[text()='हिंदी']")
    public WebElement HindiLanguage;

    @FindBy(xpath = "//p[text()='How may I address you?']")
    public WebElement AddressYouEnglish;

    @FindBy(xpath = "//p[text()='मैं आपको कैसे संबोधित कर सकता हूँ?']")
    public WebElement AddressYouHindi;

    @FindBy(xpath = "//textarea[@class='ant-input ori-tb-pad-3 textAreaField']")
    public WebElement ChatBoxTextArea;

    @FindBy(xpath = "//p[text()='It seems the name you provided is incomplete. Please make sure to enter your full name.']")
    public WebElement IncompleteNameError;

    //public  List<WebElement> ChatBotButtons = driver.findElements(By.xpath("//div[@class='ori-animated ori-fade-in ori-flex-row ori-flex-wrap ori-outer-btn-container'])[2]/button"));
    @FindBy(xpath = "(//div[@class='ori-animated ori-fade-in ori-flex-row ori-flex-wrap ori-outer-btn-container'])[2]/button")
    public List<WebElement> ChatBotButtons;

    @FindBy(xpath = "//div[@class='ori-lr-pad-5 ori-cursor-ptr']")
    public WebElement ChatBotCloseButton;

    @FindBy(xpath = "//p[text()='Do you want to close this session?']")
    public WebElement CloseSessionText;

    @FindBy(xpath = "//span[text()='Confirm']")
    public WebElement ConfirmButton;

    @FindBy(xpath = "//span[text()='Skip']")
    public WebElement SkipFeedback;

    public boolean isOneBotButtonDisplayed() {
        waitForElementToBeClickable(driver, OneBotButton, 10);
        return OneBotButton.isDisplayed();
    }

    public void  waitForElement() throws InterruptedException {
        Thread.sleep(3000);
       }

}

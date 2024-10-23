package com.tvs.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static Utils.ExplicitWait.waitForElementToBeClickable;


public class TestRidePage
{
    WebDriver driver;

    public TestRidePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="name")
    public WebElement name;

    @FindBy(id="phone")
    public WebElement phone;

    @FindBy(id="requestOTP")
    public WebElement requestOTP;

    @FindBy(id="userotpNumber")
    public WebElement userOtpNumber;

    @FindBy(id="localityPlaceHolder")
    public WebElement pinCode;

    @FindBy(id="submitTestRide")
    public WebElement submitTestRide;

    @FindBy(xpath = "//div[@class='verticleCenter']/p")
    public WebElement ConfirmMsg;


    @FindBy(xpath = "//a[contains(text(), 'Book a Test Ride')]")
    public WebElement ntorqTestride;

    @FindBy(xpath = "//button[@class='btn blueBorder btnReset']")
    public WebElement reset;

    @FindBy(className = "langCont")
    public WebElement popUp;

    @FindBy(xpath = "//div[@class='selectLang']/a[@data-name='English']")
    public WebElement selectLang;

    @FindBy(id="name-error")
    public  WebElement nameError;

    @FindBy(id="phone-error")
    public WebElement phoneError;
    @FindBy(id="localityPlaceHolder-error")
    public WebElement pincodeError;
    @FindBy(id = "dealer-error")
    public WebElement dealerError;
    @FindBy(id="userotpNumber-error")
    public WebElement otpError;

    @FindBy(xpath="//div[@class='group my']/input[@value='Detect']")
    public WebElement locDetect;

    @FindBy(id = "dealer")
    private WebElement dealerDropdown;
    public void EnterName(String username)
    {
        name.sendKeys(username);
    }

    public void EnterPhone(String mobileNumber)
    {
        phone.sendKeys(mobileNumber);
    }

    public void ClickRequestOtp()
    {
        requestOTP.click();
    }

    public void enterOtp(String otp)
    {
        userOtpNumber.sendKeys(otp);
    }

    public void enterPin(String pin)
    {
        pinCode.sendKeys(pin);
    }

    public void SubmitTestRide()
    {
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("window.scrollBy(0,400)");
        WebElement clickSubmit=waitForElementToBeClickable(driver, submitTestRide,10);
        clickSubmit.click();
        //submitTestRide.click();
    }

    public String ConfirmMsg()
    {
        return ConfirmMsg.getText();

    }

    public void ClickLocDetect()
    {
        locDetect.click();
    }

    public void ClickNtorqTestRide() throws InterruptedException
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,600)");
        WebElement clicktestride=waitForElementToBeClickable(driver, ntorqTestride,5);
        clicktestride.click();
    }

    public void ClickResetButton()
    {
        reset.click();
    }

    public void clickDealerDropdown()
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,400)");
        //WebElement clicktestride=waitForElementToBeClickable(driver, dealerDropdown,5);
        Select dropdown= new Select(dealerDropdown);
        dropdown.selectByValue("61335");
    }

}

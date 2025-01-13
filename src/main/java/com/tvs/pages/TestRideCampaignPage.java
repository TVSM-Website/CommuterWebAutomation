package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static Utils.ExplicitWait.*;

public class TestRideCampaignPage {


    WebDriver driver;

    public TestRideCampaignPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public By name=By.id("name");



    @FindBy(id = "mobilewithgenerateotp")
    public WebElement phone;

    @FindBy(xpath = "//button[@class='link-button highlight']")
    public WebElement requestOTP;

    @FindBy(xpath = "//button[@type='button' and text()='Request OTP']")
    public WebElement reqOtpdisabled;

    @FindBy(id = "otpwithresendbtn")
    public WebElement userOtpNumber;

    @FindBy(id = "pincode")
    public WebElement pinCode;

    @FindBy(xpath = "//button[@aria-label='testride-submit-button']")
    public WebElement submitTestRide;

    public By CampsubmitButton = By.xpath("//button[@class='testride-submit-button ']");

    public By ConfirmMsg= By.xpath("//div[@class='thankyou-wrapper']//h2[contains(text(), 'Thank you')]");


    @FindBy(xpath = "//a[contains(text(), 'Book a Test Ride')]")
    public WebElement ntorqTestride;

    @FindBy(xpath = "//button[@class='btn blueBorder btnReset']")
    public WebElement reset;

    @FindBy(className = "langCont")
    public WebElement popUp;

    @FindBy(xpath = "//div[@class='selectLang']/a[@data-name='English']")
    public WebElement selectLang;

    @FindBy(xpath = "//div[@class='error-message']")
    public WebElement nameError;

    @FindBy(xpath = "//div[text()='Mobile should have exact 10 digits.']")
    public WebElement phoneError;

    @FindBy(id = "localityPlaceHolder-error")
    public WebElement pincodeError;

    @FindBy(id = "dealer-error")
    public WebElement dealerError;

    @FindBy(xpath = "//div[@class='otp-field-parent']/div/div[@class='error-message']")
    public WebElement otpError;

    @FindBy(xpath = "//div[text()='Otp should have exact 4 digits.']")
    public WebElement incorrectOtp;

    @FindBy(xpath = "//button[text()='Detect']")
    public WebElement locDetect;

    @FindBy(xpath = "//div[text()='Location is required.']")
    public WebElement locError;

    public By vehicleDropdown = By.xpath("//div[@class='vehicle-selected-area']");


    @FindBy(id = "dealer")
    private WebElement dealerDropdown;

    public void EnterName(String username)
    {
        waitForVisibilityOfElementLocated(driver, name, 10);
        driver.findElement(name).sendKeys(username);
    }

    public void EnterPhone(String mobileNumber) {
        phone.sendKeys(mobileNumber);
    }

    public void ClickRequestOtp() {
        requestOTP.click();
    }

    public void enterOtp(String otp) {
        userOtpNumber.sendKeys(otp);
    }

    public void enterPin(String pin) {
        pinCode.sendKeys(pin);
    }

    public void SubmitTestRide() {
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("window.scrollBy(0,400)");
        WebElement clickSubmit = waitForElementToBeClickable(driver, submitTestRide, 10);
        clickSubmit.click();
        //submitTestRide.click();
    }


    public void ClickLocDetect() {
        locDetect.click();
    }

    public void ClickNtorqTestRide() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)");
        WebElement clicktestride = waitForElementToBeClickable(driver, ntorqTestride, 5);
        clicktestride.click();
    }

    public void ClickResetButton() {
        reset.click();
    }

    public void clickDealerDropdown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");
        //WebElement clicktestride=waitForElementToBeClickable(driver, dealerDropdown,5);
        Select dropdown = new Select(dealerDropdown);
        dropdown.selectByValue("61335");
    }

}



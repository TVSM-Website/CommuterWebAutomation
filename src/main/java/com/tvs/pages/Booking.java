package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Booking
{
    WebDriver driver;
    public Booking(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath="//div[@id='slick-slide00']//div//a")
    private WebElement selectVehicle;

    @FindBy(xpath ="//div[@id='slick-slide02']//div//a")
    private WebElement jupiterVehicle;

    @FindBy(xpath="//ul[@class='nav nav-pills nav-fill iqube-fill']/li[2]/a")
    private WebElement variant;

    @FindBy(id="submitVariant")
    private WebElement next;

    private final By location=By.xpath("//input[@placeholder='Select Pincode / City']");

    @FindBy(id="ui-id-44")
    private WebElement city;

    @FindBy(id="submitDealer")
    public WebElement submitDealer;

    private static final By mobileNumber=By.xpath("//input[@type='number']");
    private final By name=By.xpath("//input[@name='name']");

    @FindBy(xpath="//input[@type='email']")
    private WebElement emailId;

    @FindBy(id="verifyotp")
    public WebElement Verifyotp;

    @FindBy(xpath="//button[@class='roboto-bold w-100 m-0 btn btn-primary']")
    private WebElement submitOtp;


    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement marketCheckmark;

    @FindBy(xpath = "//div[@class='verify-box']/span")
    public static WebElement MobileVerified;

    @FindBy(xpath = "//input[@id='checkbox-input-privacy']")
    private WebElement tncCheckBox;

    @FindBy(xpath = "//button[contains(text(),'Verify Mobile Number')]")
    public WebElement verifyMobileNumber;

    @FindBy(xpath = "//button[contains(text(),'Pay')]")
    public WebElement payButton;

    @FindBy(xpath = "(//div[@id='buttons']//a[contains(@class, 'cancel')])[1]")
    public WebElement cancelPayment;

    @FindBy(xpath = "//a[contains(@class,'confirmCancel')]")
    public WebElement confirmCancel;

    private final By variantNext=By.cssSelector("button.roboto-medium.fw-semibold.btn.btn-primary:nth-of-type(2)");


    public void clickConfirmCancel()
    {
        confirmCancel.click();
    }
    public void clickCancelPayment()
    {
        cancelPayment.click();
    }

    public void clickPayButton()
    {
        payButton.click();
    }
    public void clickVerifyMobileNumber()
    {
        verifyMobileNumber.click();
    }
    public void clickTncCheckBox()
    {
        tncCheckBox.click();
    }


    public void clickVariantNext()
    {
        driver.findElement(variantNext).click();
    }

    public void submitOtp()
    {
        submitOtp.click();
    }

    public void clickVehicle()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement bookingLink = wait.until(ExpectedConditions.elementToBeClickable(jupiterVehicle));
        Actions actions= new Actions(driver);
        actions.scrollToElement(jupiterVehicle).build().perform();
        bookingLink.click();
    }

    public void clickNext() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nextClick = wait.until(ExpectedConditions.elementToBeClickable(next));
        Actions actions= new Actions(driver);
        actions.moveToElement(next).click().build().perform();
    }

    public void selectLocation(String loc)
    {
        driver.findElement(location).sendKeys(loc);
    }

    public void setCity()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement clickCity = wait.until(ExpectedConditions.elementToBeClickable(city));
        clickCity.click();
    }

    public void submitDealer()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement clickdealerNext = wait.until(ExpectedConditions.elementToBeClickable(submitDealer));
        Actions actions= new Actions(driver);
        actions.scrollToElement(submitDealer).build().perform();
        clickdealerNext.click();
    }

    public static String MobileVerified() throws InterruptedException {
        Thread.sleep(3000);
        return MobileVerified.getText();
    }

    public void EnterNumber(String num)
    {
        driver.findElement(mobileNumber).sendKeys(num);
    }

    public void setName(String custName)
    {
        driver.findElement(name).sendKeys(custName);
    }

    public void setEmailid(String email)
    {
        emailId.sendKeys(email);
    }

    public void clickverifyOtp()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement otpclick = wait.until(ExpectedConditions.elementToBeClickable(Verifyotp));
        Actions actions= new Actions(driver);
        actions.scrollToElement(Verifyotp).build().perform();
        otpclick.click();
    }

    @FindBy(xpath = "//div[@id='OPTNBK']//span[text()='Net Banking'][2]")
    private static WebElement netbanking;

    @FindBy(id="netBankingBank")
    private static WebElement NetBankingDropdown;

    @FindBy(xpath = "//a[@class='primary-button primary-button-bg radius4 SubmitBillShip'][1]")
    private static WebElement makePayment;

    public static void clickNetBanking()
    {
        netbanking.click();
    }

    public static void selectNetBankingOption(String optionText)
    {
        Select dropdown = new Select(NetBankingDropdown);
        dropdown.selectByVisibleText(optionText);
    }

    public static void ClickMakePayment()
    {
        makePayment.click();
    }



}

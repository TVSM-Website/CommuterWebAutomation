package com.tvs.pages;

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

    @FindBy(id="localityPlaceHolder")
    private WebElement location;

    @FindBy(id="ui-id-44")
    private WebElement city;

    @FindBy(id="submitDealer")
    public WebElement submitDealer;

    @FindBy(id="number")
    private static WebElement mobilenumber;

    @FindBy(id="name")
    private WebElement name;

    @FindBy(id="email")
    private WebElement emailid;

    @FindBy(id="verifyotp")
    public WebElement Verifyotp;

    @FindBy(id="btnVerifyOTP")
    public WebElement submitOtp;

    @FindBy(id="PaymentOptionSubmit")
    public  WebElement submitPayment;

    @FindBy(id="SubmitPaymentSummary")
    private WebElement pay;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement marketCheckmark;

    @FindBy(xpath = "//div[@class='verify-box']/span")
    public static WebElement MobileVerified;


    public void clickVehicle()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement bookingLink = wait.until(ExpectedConditions.elementToBeClickable(jupiterVehicle));
        Actions actions= new Actions(driver);
        actions.scrollToElement(jupiterVehicle).build().perform();
        bookingLink.click();
    }

    public void setVariant()
    {
        variant.click();
    }

    public void clickNext() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nextclick = wait.until(ExpectedConditions.elementToBeClickable(next));
        Actions actions= new Actions(driver);
        actions.moveToElement(next).click().build().perform();
    }

    public void selectLocation(String loc)
    {
        location.sendKeys(loc);
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
        mobilenumber.sendKeys(num);
    }

    public void setName(String custName)
    {
        name.sendKeys(custName);
    }

    public void setEmailid(String email)
    {
        emailid.sendKeys(email);
    }

    public void clickverifyOtp()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement otpclick = wait.until(ExpectedConditions.elementToBeClickable(Verifyotp));
        Actions actions= new Actions(driver);
        actions.scrollToElement(Verifyotp).build().perform();
        otpclick.click();
    }
    public void setSubmitOtp()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement submitotp = wait.until(ExpectedConditions.elementToBeClickable(submitOtp));
        Actions actions= new Actions(driver);
        actions.scrollToElement(submitOtp).build().perform();
        submitotp.click();
        //submitOtp.click();
    }

    public void setSubmitPayment()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        Actions actions= new Actions(driver);
        actions.scrollToElement(submitPayment).build().perform();
        WebElement submitotp = wait.until(ExpectedConditions.elementToBeClickable(submitPayment));
        submitotp.click();
        //submitPayment.click();
    }

    public void clickPay()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        Actions actions= new Actions(driver);
        actions.scrollToElement(pay).build().perform();
        WebElement clickpayment = wait.until(ExpectedConditions.elementToBeClickable(pay));
        clickpayment.click();
    }

    public static boolean disableNumber()
    {
        mobilenumber.isEnabled();
        return false;
    }

    public void disableName()
    {
        name.isEnabled();
    }

    public void disableEmailId()
    {
        emailid.isEnabled();
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

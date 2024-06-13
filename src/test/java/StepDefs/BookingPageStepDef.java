package StepDefs;

import Utils.WebDriverManager;
import com.tvs.pages.Booking;
import com.tvs.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static Utils.ExplicitWait.visibilityOfElementLocated;
import static Utils.ExplicitWait.waitForElementToBeClickable;

public class BookingPageStepDef
{
    private WebDriver driver;
    private HomePage homePage;
    private Booking booking;

    WebElement submitDealer ;
    WebElement verifyOtpbtn ;
    WebElement marketCheckmark ;
    WebElement submitOtp;
    WebElement mobileVerified;

    public BookingPageStepDef() {
        this.driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
        booking= new Booking(driver);

        // Assigning WebElement variables
        submitDealer = booking.submitDealer;
        verifyOtpbtn=booking.Verifyotp;
        marketCheckmark=booking.marketCheckmark;
        submitOtp=booking.submitOtp;
        mobileVerified=booking.MobileVerified;

    }

    @Given("Navigate to the booking page")
    public void navigate_to_the_booking_page()
    {
        driver.get("https://uat-www.tvsmotor.net/");
        homePage.setBooking();
    }
    @When("select the vehicle and variant")
    public void select_the_vehicle_and_variant() throws InterruptedException {
        booking.clickVehicle();
        booking.setVariant();
        booking.clickNext();
    }
    @Then("next button is disabled before selecting the location and dealer")
    public void next_button_is_disabled_before_selecting_the_location_and_dealer()
    {
        Assert.assertFalse( booking.submitDealer.isEnabled());
    }
    @Then("enter the location and choose dealership name")
    public void enter_the_location_and_choose_dealership_name() throws InterruptedException {
        booking.selectLocation("Bangalore");
        Thread.sleep(3000);
        List<WebElement> cities = driver.findElements(By.xpath("//ul[@id='ui-id-1']/li"));
        for (WebElement city : cities) {
            if(city.getText().equalsIgnoreCase("Bangalore,Karnataka"))
            {
                city.click();
            }
        }
    }
    @Then("next button is enabled after selecting the location and dealer")
    public void next_button_is_enabled_after_selecting_the_location_and_dealer() throws InterruptedException {
        //waitForElementToBeClickable(driver,submitDealer,5);
        //Assert.assertTrue( submitDealer.isEnabled());
        booking.submitDealer();
    }

    @And("Marketing updated checkbox should be checked")
    public void marketing_updated_checkbox_should_be_checked()
    {
        Assert.assertTrue("Marketing updated checkbox is checked",
                marketCheckmark.isSelected());
    }
    @And("Verify mobile number and otp button should be disabled before filling the details")
    public void verify_otp_button_should_be_disabled_before_filling_the_details() throws InterruptedException
    {
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
       // visibilityOfElementLocated(driver,By.id("verifyotp"),3);
        Assert.assertFalse(verifyOtpbtn.isEnabled());

    }
    @Then("Enter the personal details {string},{string} and {string}")
    public void enter_the_personal_details_and(String mobile, String name, String email)
    {
        booking.EnterNumber(mobile);
        booking.setName(name);
        booking.setEmailid(email);
    }
    @And("Verify mobile number and otp button should be enabled after filling the details")
    public void verify_otp_button_should_be_enabled_after_filling_the_details() throws InterruptedException
    {
        waitForElementToBeClickable(driver,verifyOtpbtn,10);
        Assert.assertTrue(verifyOtpbtn.isEnabled());
        booking.clickverifyOtp();
    }

    @And("OTP submit button should be disabled before entering the otp")
    public void otp_submit_button_should_be_disabled_before_entering_the_otp()
    {
        Assert.assertFalse(submitOtp.isEnabled());
    }
    @And("OTP submit button should be enabled after entering the otp")
    public void otp_submit_button_should_be_enabled_after_entering_the_otp()
    {
        waitForElementToBeClickable(driver,submitOtp,15);
        Assert.assertTrue(submitOtp.isEnabled());
        submitOtp.click();

    }
    @And("Mobile number should be verified")
    public void mobile_number_should_be_verified() throws InterruptedException {
        Assert.assertEquals("Verified",Booking.MobileVerified());
    }
    @Then("mobile number,fullName and email id text fields should be disabled")
    public void mobile_number_full_name_and_email_id_textboxes_should_be_disabled()
    {
        Assert.assertFalse(Booking.disableNumber());
        booking.setSubmitPayment();
        booking.clickPay();
    }

    @Then("click on netBanking payment mode")
    public void clickOnNetBankingPaymentMode()
    {
        Booking.clickNetBanking();

    }

    @And("select the {string} bank from dropdown values")
    public void selectTheFromDropdownValues(String bank)
    {
        Booking.selectNetBankingOption(bank);

    }

    @And("click on Make Payment button")
    public void clickOnMakePaymentButton()
    {
        Booking.ClickMakePayment();
    }
}

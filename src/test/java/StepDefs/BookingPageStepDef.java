package StepDefs;

import Utils.ExplicitWait;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.Booking;
import com.tvs.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

import static Utils.ExplicitWait.*;
import static Utils.Utilities.scrollByPixels;
import static Utils.Utilities.scrollToElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class BookingPageStepDef {
    private WebDriver driver;
    private HomePage homePage;
    private Booking booking;

    WebElement submitDealer;
    WebElement verifyOtpbtn;
    WebElement marketCheckmark;
    WebElement submitOtp;
    WebElement mobileVerified;
    String environment;
    WebDriverWait wait;

    public BookingPageStepDef() {
        this.driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
        booking = new Booking(driver);

        // Assigning WebElement variables
        submitDealer = booking.submitDealer;
        verifyOtpbtn = booking.Verifyotp;
        marketCheckmark = booking.marketCheckmark;
        mobileVerified = booking.MobileVerified;

    }

    @Given("navigate to tvs website on {string} environment")
    public void navigateToTvsWebsiteOnEnvironment(String env) {
        environment = env;
        String url = Utilities.getUrl(environment);
        driver.get(url);
        //HandleAlert(driver,"PoGomjipkB");
        Utilities.verifyUrl(driver, environment);
    }

    @When("user clicks on buy vehicle button")
    public void userClicksOnBuyVehicleButton()
    {
       // homePage.clickBuyVehicle();
        //scrollToElement(driver.findElement(By.xpath("(//ul[@class='slick-dots'])[2]")));

    }

    @When("select the vehicle {string} from the list")
    public void selectTheVehicleFromTheList(String vehicle)
    {
        scrollByPixels(550);
        boolean vehicleFound = false;

        while (!vehicleFound)
        {
            waitForVisibilityOfElementLocated(driver,By.cssSelector(".slick-slide:not(.slick-cloned) .card-title"),10);
            List<WebElement> vehicleCards = driver.findElements(By.cssSelector(".slick-slide:not(.slick-cloned) .card-title"));

            for (WebElement card : vehicleCards)
            {
                if (card.getText().trim().equalsIgnoreCase(vehicle))
                {
//                    card.click();
//                    vehicleFound = true;
//                    break;
                    Actions actions = new Actions(driver);
                    actions.moveToElement(card).click().build().perform();  // More reliable click
                    vehicleFound = true;
                    break;
                }
            }

            if (!vehicleFound) {
                WebElement nextArrow = driver.findElement(By.xpath("(//button[@class='slick-arrow slick-next'])[2]"));
                if (nextArrow.isDisplayed())
                {
                    nextArrow.click();
                } else {
                    break; // Exit loop if next button is not available
                }
            }
        }

    }


    @Then("select the variant {string}")
    public void selectTheVariant(String variant) throws InterruptedException {
        Thread.sleep(3000);
        //scrollToElement(driver.findElement(By.xpath("(//div[@class='specification-details-wrap'])[1]")));
        scrollByPixels(220);
        List<WebElement> variantElements = driver.findElements(By.cssSelector(".generic-nav.nav .nav-item a"));

        for (WebElement variantElement : variantElements) {
            if (variantElement.getText().trim().equalsIgnoreCase(variant))
            {
                variantElement.click();
                return; // Exit once the variant is found and clicked
            }
        }
        throw new NoSuchElementException("Variant " + variant + " not found!");
    }

    @And("click on next button")
    public void clickOnNextButton()
    {
        booking.clickVariantNext();
    }


    @Then("enter the location and choose dealership name")
    public void enter_the_location_and_choose_dealership_name() throws InterruptedException {
        WebElement locationInput = driver.findElement(By.xpath("//input[@name='pincode']"));
        locationInput.sendKeys("560038");
        List<WebElement> cities = driver.findElements(By.xpath("//div[@class='location-suggestion-container']/div"));

        for (WebElement city : cities)
        {
            if (city.getText().trim().equalsIgnoreCase("560038-Indiranagar (Bangalore)"))
            {
                city.click();
                break;
            }
        }

        Thread.sleep(3000);

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


    @And("click on terms and conditions checkbox")
    public void clickOnTermsAndConditionsCheckbox()
    {
        booking.clickTncCheckBox();
    }

    @And("click on verify mobile number button")
    public void clickOnVerifyMobileNumberButton()
    {
        booking.clickVerifyMobileNumber();
    }

    @Then("Click on submit button to verify otp")
    public void clickOnSubmitButtonToVerifyOtp() throws InterruptedException
    {
        Thread.sleep(10000);
        booking.submitOtp();
    }

    @And("click on the Pay button")
    public void clickOnThePayButton()
    {
        waitForElementToBeClickable(driver,booking.payButton,10);
        booking.clickPayButton();
    }

    @And("click on the cancel and confirm cancel button")
    public void clickOnTheCancelPaymentButton()
    {
        waitForElementToBeClickable(driver,booking.cancelPayment,10);
        booking.clickCancelPayment();
        waitForElementToBeClickable(driver,booking.confirmCancel,10);
        booking.clickConfirmCancel();

    }


}

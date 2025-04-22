package StepDefs;

import Utils.ExplicitWait;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.GenericTestRidePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static Utils.Utilities.scrollToElement;

public class GenericTestRideStepDef
{
    WebDriver driver;
    String env;
    GenericTestRidePage genericTestRidePage;

    public GenericTestRideStepDef()
    {
        this.driver = WebDriverManager.getDriver();
        genericTestRidePage = new GenericTestRidePage(driver);
    }

    @Given("the user is on the test ride page on {string}")
    public void theUserIsOnTheTestRidePageOn(String environment)
    {
        env=environment;
        String url = Utilities.getUrl(environment);
        driver.get(url);
        //HandleAlert(driver,"PoGomjipkB");
        Utilities.verifyUrl(driver, environment);
    }

    @When("the user selects {string} from the vehicle dropdown")
    public void theUserSelectsFromTheVehicleDropdown(String vehicleName) throws InterruptedException
    {

        // Scroll the main page slightly to make the dropdown visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 450)");

        // Click to open the dropdown
        genericTestRidePage.clickVehicleDropdown();

        // Locate the dropdown container (ensure this is the correct selector for the scrollable area)
        WebElement dropdownContainer = driver.findElement(By.cssSelector(".vehicleSelectBoxListing"));

        // Get all visible vehicles in the dropdown
        List<WebElement> vehicles = dropdownContainer.findElements(By.cssSelector("ul li a"));

        boolean isVehicleFound = false;

        // Try finding the vehicle while simulating keypresses to scroll through options
        while (!isVehicleFound) {
            for (WebElement vehicle : vehicles) {
                if (vehicle.getText().equalsIgnoreCase(vehicleName)) {
                    // Wait until the element is clickable and click it
                    ExplicitWait.waitForElementToBeClickable(driver, vehicle, 10);
                    vehicle.click();
                    isVehicleFound = true;
                    break;
                }
            }

            // If the vehicle is not found, simulate a keyboard event to scroll
            if (!isVehicleFound) {
                // Click the bottom of the scrollbar dragger to scroll down
                WebElement scrollBottom = driver.findElement(By.cssSelector("#mCSB_1_dragger_vertical"));
                Actions actions = new Actions(driver);

                // Move the click to the bottom of the dragger
                actions.moveToElement(scrollBottom, 0, scrollBottom.getSize().height - 10).click().perform();

                // Wait for a moment to allow dropdown to update
                Thread.sleep(1000);  // Allow time for the dropdown to load new vehicle options

            }
        }
    }

    @When("user selects the {string} from the variant dropdown")
    public void userSelectsTheFromTheVariantDropdown(String variant)
    {
        //scrollToElement(driver.findElement(By.id("variant")));
        WebElement variantDropdown = driver.findElement(By.id("variant"));

        Select selectVariant = new Select(variantDropdown);

        try {
            Thread.sleep(2000);
            selectVariant.selectByVisibleText(variant);
            System.out.println("Variant selected: " + variant);
        } catch (NoSuchElementException e) {
            System.out.println("Variant '" + variant + "' not found in the dropdown.");
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @And("Request otp for entered mobile number and verify the OTP")
    public void requestOtpForEnteredMobileNumberAndVerifyTheOTP() throws InterruptedException {
        genericTestRidePage.ClickRequestOtp();
        Thread.sleep(7000);
    }
}

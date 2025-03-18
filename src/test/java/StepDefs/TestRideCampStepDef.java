package StepDefs;

import Utils.DataBaseConnection;
import Utils.ExplicitWait;
import Utils.WebDriverManager;
import com.tvs.pages.TestRideCampaignPage;
import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static Utils.DataBaseConnection.*;
import static Utils.DataBaseConnection.password;
import static Utils.ExplicitWait.*;

public class TestRideCampStepDef
{
    private final WebDriver driver;

    private final TestRideCampaignPage testRidePage;

    String parentWindowHandle;

    WebElement requestOTP;
    WebElement submitTestRide;
    WebElement LangSelPopUp;
    WebElement engLangsel;
    WebElement nameError;
    WebElement phoneError;
    WebElement otpError;
    WebElement pincodeError;
    WebElement dealerError;
    By name;
    WebElement phone;
    WebElement userOtpNumber;
    WebElement pinCode;
    WebElement reqOtpdisabled;
    public static String selectedVehicle;
    private static Properties properties = new Properties();
    String vehicleSelected;
    String phoneNumber;
    String userName;
    String selVehicle;
    public TestRideCampStepDef() {
        this.driver = WebDriverManager.getDriver();
        testRidePage= new TestRideCampaignPage(driver);
        requestOTP=testRidePage.requestOTP;
        submitTestRide=testRidePage.submitTestRide;
        LangSelPopUp=testRidePage.popUp;
        engLangsel=testRidePage.selectLang;
        nameError=testRidePage.nameError;
        phoneError=testRidePage.phoneError;
        otpError=testRidePage.otpError;
        pincodeError=testRidePage.pincodeError;
        dealerError=testRidePage.dealerError;
        name=testRidePage.name;
        phone=testRidePage.phone;
        userOtpNumber=testRidePage.userOtpNumber;
        pinCode=testRidePage.pinCode;
        reqOtpdisabled=testRidePage.reqOtpdisabled;

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }


    @When("user fills the {string}, {string} and {string}")
    public void fill_the_details(String name, String phone, String pincode) throws InterruptedException {
        Thread.sleep(2000);
        userName=name;
        phoneNumber=phone;
        testRidePage.EnterName(name);
        testRidePage.EnterPhone(phone);
        //testRidePage.pinCode.clear();
        //testRidePage.enterPin(pincode);
    }

    @Then("validate invalid location pincode error message")
    public void clickOnDetectButtonGetLiveLocation() throws InterruptedException {
        //testRidePage.ClickLocDetect();
        testRidePage.pinCode.sendKeys(Keys.CONTROL, "a");  // Select all text
        testRidePage.pinCode.sendKeys(Keys.BACK_SPACE);  // Press backspace to clear
        Assert.assertEquals("Location is required.",testRidePage.locError.getText());
    }



    @Then("Request otp button is {string}")
    public void request_otp_button(String otpbtnStatus) throws InterruptedException {
        boolean isEnabled = requestOTP.isEnabled();

        if (otpbtnStatus.equalsIgnoreCase("enabled"))
        {
            Assert.assertTrue(isEnabled);
        }
        else if
        (otpbtnStatus.equalsIgnoreCase("disabled"))
        {
            Thread.sleep(2000);
            Assert.assertEquals("Phone number minimum length must be 10",testRidePage.phoneError.getText());
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid otpButtonStatus value: " + otpbtnStatus);
        }

    }



    @Then("Request otp for entered mobile number and verify OTP")
    public void RequestOTP() throws InterruptedException {
        testRidePage.ClickRequestOtp();
        //testRidePage.enterOtp("1234");
        Thread.sleep(10000);

    }
    @And("Click on the submit button")
    public void clickOnSubmitButton() throws InterruptedException
    {
        testRidePage.SubmitTestRide();
        Thread.sleep(10000);
    }
    @And("Verify the confirmation message after form is submitted")
    public void VerifyTheConfirmation_message() throws InterruptedException {
        waitForVisibilityOfElementLocated(driver,testRidePage.ConfirmMsg,15);

        String thankMsg = driver.findElement(testRidePage.ConfirmMsg).getText();
        Thread.sleep(3000);
        System.out.println("Thank you message: " + thankMsg);

        if (thankMsg.toLowerCase().contains("thank you")) {
            System.out.println("The confirmation message contains Thank you");
        } else {
            throw new AssertionError("The confirmation message does not contain 'Thank you'.");
        }


    }
    @And("Verify data in the database after submitting the test Ride form")
    public void verifyDataInTheDatabaseAfterSubmitting() throws IOException
    {
        LocalDateTime scriptStartTime = LocalDateTime.now();

        Properties modelIdProperties = new Properties();
        Properties partIdProperties = new Properties();

        try (FileInputStream modelIdStream = new FileInputStream("src/test/Resources/ModelIds.properties");
             FileInputStream partIdStream = new FileInputStream("src/test/Resources/PartID.properties")) {

            modelIdProperties.load(modelIdStream);
            partIdProperties.load(partIdStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties files", e);
        }

        Connection connection = null;
        try {
            // Establish database connection
            connection = DataBaseConnection.getConnection(server, database, username, password);

            // Query to fetch the 'Request' column based on the dynamic mobile number
            String query = "SELECT tt.Request " +
                    "FROM TestRideRegistrations t " +
                    "INNER JOIN Users u ON t.UserId = u.userid " +
                    "INNER JOIN mstBSVIVariantPrice m ON t.mstBSVIVariantPriceId = m.id " +
                    "INNER JOIN transactionlogs tt ON t.Transactionlogid = tt.Transactionlogid " +
                    "WHERE u.alternatecontactnumber = '" + phoneNumber + "' " +
                    "ORDER BY t.CreatedDate DESC";

            connection.createStatement();
            ResultSet resultSet = DataBaseConnection.executeQuery(connection, query);

            if (resultSet.next()) {
                // Retrieve the JSON from the 'Request' column
                String requestJson = resultSet.getString("Request");
                System.out.println("Request JSON from DB: " + requestJson);

                // Expected JSON structure for validation

                // Parse the JSON and validate the contents
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> requestData = objectMapper.readValue(requestJson, new TypeReference<Map<String, Object>>() {});

                System.out.println("modelidFile :"+modelIdProperties.getProperty(selVehicle.split("_")[0]));
                System.out.println("PartId :"+partIdProperties.getProperty(selVehicle.split("_")[0]));

                // Validate key fields in the JSON
                Assert.assertEquals("Customer name does not match", userName, requestData.get("customeR_NAME"));
                Assert.assertEquals("Mobile number does not match", phoneNumber, requestData.get("mobilE_NUMBER"));
                Assert.assertEquals("ModelId not Matched with Db record",(modelIdProperties.getProperty(selVehicle.split("_")[0])), requestData.get("modeL_ID").toString());
                Assert.assertEquals("PartId not Matched with Db record",(partIdProperties.getProperty(selVehicle.split("_")[0])),requestData.get("parT_ID").toString());



                System.out.println("Validation successful for Request JSON!");
            } else {
                throw new AssertionError("No record found for the provided mobile number: " + phoneNumber);
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while validating database entry", e);

        } finally {
            // Close connections
            DataBaseConnection.close(connection, null, null);
        }
    }

    @Given("Navigate to Campaign Test Ride page for {string}")
    public void NavigateCampaignTestRidePage(String vehicle) throws InterruptedException, IOException
    {
        selVehicle=vehicle;
        FileInputStream fileInputStream = new FileInputStream("src/test/Resources/testride_urls.properties");
        properties.load(fileInputStream);
        selectedVehicle=properties.getProperty(vehicle);
        driver.get(selectedVehicle);
//        try {
//            WebElement popup = LangSelPopUp;
//            if (popup.isDisplayed())
//            {
//                // Find the close button
//                //System.out.println("displayed:--");
//
////                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
////                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='selectLang']/a[@data-name='English']")));
//                WebElement clickLang=waitForElementToBeClickable(driver,engLangsel,5);
//                clickLang.click();
//
////                WebElement closeButton = driver.findElement(By.xpath("//a[@class='closePop']"));
////                closeButton.click();
//            }
//        } catch (NoSuchElementException | StaleElementReferenceException ignored) {
//            // Popup not found or not displayed
//        }
        parentWindowHandle = driver.getWindowHandle();
        System.out.println("parent "+parentWindowHandle);
        //testRidePage.ClickNtorqTestRide();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(parentWindowHandle))
            {
                driver.switchTo().window(windowHandle);
                System.out.println("window "+windowHandles);
                System.out.println(driver.getTitle());
            }
        }

    }


    @Then("URL should indicate redirection to the UAT environment")
    public void theURLShouldIndicateRedirectionToUATEnvironment()
    {
        Assert.assertTrue(driver.getCurrentUrl().contains("uat"));

    }

    @When("user fills incorrect {string}, {string} and {string}")
    public void userFillsIncorrectAnd(String name, String phone, String arg2) throws InterruptedException {
        Thread.sleep(2000);
        userName=name;
        phoneNumber=phone;
        testRidePage.EnterName(name);
        waitForElementToBeVisible(driver,nameError,10,testRidePage.nameError.getText());
        Assert.assertEquals("Please enter a valid name",testRidePage.nameError.getText());
        testRidePage.EnterPhone(phone);

        testRidePage.SubmitTestRide();
        Assert.assertEquals("Mobile should have exact 10 digits.",testRidePage.phoneError.getText());
    }

    @Then("request otp button is disabled")
    public void requestOtpButtonIs() throws InterruptedException
    {
        System.out.println("Is Request OTP Button Enabled? " + reqOtpdisabled.isEnabled());
        Assert.assertFalse("The Request OTP button is not disabled.",reqOtpdisabled.isEnabled() );

    }

    @And("entered incorrect otp and verify the error message")
    public void enteredIncorrectOtpAndVerifyTheErrorMessage() throws InterruptedException {
        testRidePage.enterOtp("234");
        WebElement clickSubmit = waitForElementToBeClickable(driver, submitTestRide, 10);
        clickSubmit.click();
        waitForElementToBeVisible(driver,otpError,10,testRidePage.otpError.getText());
        Assert.assertEquals("Otp should have exact 4 digits.",testRidePage.incorrectOtp.getText());
    }

    @And("click on the detect button to get live location")
    public void clickOnTheDetectButtonToGetLiveLocation()
    {
        testRidePage.ClickLocDetect();


    }

    @When("user Fill the {string}, {string} and {string}")
    public void userFillTheAnd(String userName, String phone, String arg2)
    {
        waitForVisibilityOfElementLocated(driver,testRidePage.name,10);
        driver.findElement(testRidePage.name).sendKeys(userName);
        testRidePage.phone.sendKeys(phone);
        testRidePage.SubmitTestRide();

    }

    @And("click request otp for entered mobile number and verify the OTP")
    public void clickRequestOtpForEnteredMobileNumberAndVerifyTheOTP() throws InterruptedException {
        testRidePage.requestOTP.click();
        Thread.sleep(7000);
    }

    @When("user selects {string} from the vehicle dropdown in campaign url")
    public void userSelectsFromTheVehicleDropdownInCampaignUrl(String vehicleName) throws InterruptedException {

        // Scroll the main page slightly to make the dropdown visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 450)");

        // Click to open the dropdown
        driver.findElement(testRidePage.vehicleDropdown).click();

        // Locate the dropdown container (ensure this is the correct selector for the scrollable area)
        WebElement dropdownContainer = driver.findElement(By.xpath("//div[@class='vehicle-selection-list']"));

        // Get all visible vehicles in the dropdown
        List<WebElement> vehicles = dropdownContainer.findElements(By.xpath("//div[@class='vehicle-selection-item']/div[contains(@class,'vehicle-selection-title')]"));

        boolean isVehicleFound = false;

        // Try finding the vehicle while simulating keypresses to scroll through options
        while (!isVehicleFound) {
            for (WebElement vehicle : vehicles) {
                if (vehicle.getText().equalsIgnoreCase(vehicleName))
                {
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

    @And("Click on submit button in campaign page")
    public void clickOnSubmitButtonInCampaignPage()
    {
        driver.findElement(testRidePage.CampsubmitButton).click();
    }
}

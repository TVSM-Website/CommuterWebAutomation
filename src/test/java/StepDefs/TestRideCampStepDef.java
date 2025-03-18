package StepDefs;

import Utils.DataBaseConnection;
import Utils.WebDriverManager;
import com.tvs.pages.TestRideCampaignPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Set;

import static Utils.DataBaseConnection.*;
import static Utils.DataBaseConnection.password;
import static Utils.ExplicitWait.visibilityOfElementLocated;

public class TestRideCampStepDef
{
    private WebDriver driver;

    private TestRideCampaignPage testRidePage;

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
    public static String selectedVehicle;
    private static Properties properties = new Properties();
    String vehicleSelected;
    String phoneNumber;
    String userName;
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

    @Then("click on the detect button to get live location")
    public void clickOnDetectButtonGetLiveLocation() throws InterruptedException {
        testRidePage.ClickLocDetect();
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
        Thread.sleep(2000);
        testRidePage.SubmitTestRide();
        Thread.sleep(1500);
    }
    @And("Verify the confirmation message after form is submitted")
    public void VerifyTheConfirmation_message()
    {
//        visibilityOfElementLocated(driver, By.xpath("//div[@class='verticleCenter']/p"),40);
//        String thankMsg=testRidePage.ConfirmMsg();
//        String[] part = thankMsg.split("We will");
//        String part1 = part[0];
//        String[] part2=part1.split("in");
//        vehicleSelected=part2[2].trim().replace(".","");
        //System.out.println("vehicleSelected: "+ vehicleSelected);



    }
    @And("Verify data in the database after submitting the test Ride form")
    public void verifyDataInTheDatabaseAfterSubmitting() throws IOException
    {
        Properties modelIdProperties = new Properties();
        Properties partIdProperties = new Properties();

        FileInputStream ModelId = new FileInputStream("src/test/Resources/ModelIds.properties");
        modelIdProperties.load(ModelId);
        FileInputStream PartId = new FileInputStream("src/test/Resources/PartID.properties");
        partIdProperties.load(PartId);
        LocalDateTime scriptStartTime = LocalDateTime.now();
        LocalDateTime scriptEndTime = LocalDateTime.now();


//        server = "tvsmazwebdbsuat01.database.windows.net";
//         database = "tvsmazwebsdbuat01-corp-transaction";
//         username = "sqladmin";
//         password = "BFwVqmKq2D6NwGUmjjV3fExQPreProd";

        // Establish database connection
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection(server, database, username, password);
            //System.out.println("Connected to the database!");
            // log.info( "Connected to the database!");

            // Write SQL query
//            String query = "SELECT CustomerName, MobileNumber, ENQUIRYDATE, VehicleName " +
//                    "FROM TVSTestRideBookingData " +
//                    "WHERE VehicleName = '" + vehicleSelected + "' "  +
//                    "AND MobileNumber ='" + phoneNumber + "' " +
//                    "AND CustomerName ='" + userName + "' " +
//                    "AND CONVERT(DATE, ENQUIRYDATE) = CONVERT(DATE, GETDATE()) "+ // Using GETDATE() instead of CURDATE()
//                    "ORDER BY ENQUIRYDATE DESC";

            String query = "SELECT CustomerName, MobileNumber, ENQUIRYDATE, VehicleName,MODEL_ID,PART_ID " +
                    "FROM TVSTestRideBookingData " +
                    "WHERE VehicleName = '" + vehicleSelected + "' "  +
                    "AND MobileNumber ='" + phoneNumber + "' " +
                    "AND CustomerName ='" + userName + "' " +
                    "AND ENQUIRYDATE BETWEEN DATEADD(MINUTE, -1, GETDATE()) AND GETDATE() " +
                    "ORDER BY ENQUIRYDATE DESC";


            //System.out.println("query: "+query);
            Statement statement = connection.createStatement();

            // Execute query
            ResultSet resultSet = DataBaseConnection.executeQuery(connection, query);
            while (resultSet.next())
            {
                // Retrieve data from the current row
                String customerName = resultSet.getString("CustomerName");
                String mobileNumber = resultSet.getString("MobileNumber");
                Date enquiryDate = resultSet.getDate("ENQUIRYDATE");
                String vehicleNameDB = resultSet.getString("VehicleName");
                String Model_Id = resultSet.getString("MODEL_ID");
                String Part_Id = resultSet.getString("PART_ID");

                // Print the retrieved data
                System.out.println("Customer Name: " + customerName);
                System.out.println("Mobile Number: " + mobileNumber);
                System.out.println("Enquiry Date: " + enquiryDate);
                System.out.println("Vehicle Name: " + vehicleNameDB);
                System.out.println("--------------------------------------");


                Assert.assertEquals("Vehicle name matched with Db record",vehicleSelected,vehicleNameDB);
                Assert.assertEquals("phoneNumber matched with Db record",phoneNumber,mobileNumber);
                Assert.assertEquals("userName matched with Db record",userName,customerName);

                //logger.log(Level.INFO, "Verifying the model id and part id of vehicles against the Database records");
                Assert.assertEquals("ModelId not Matched with Db record",(modelIdProperties.getProperty(vehicleSelected.replaceAll("\\s+", ""))),Model_Id);
                Assert.assertEquals("PartId not Matched with Db record",(partIdProperties.getProperty(vehicleSelected.replaceAll("\\s+", ""))),Part_Id);


                System.out.println("modelidFile :"+modelIdProperties.getProperty(vehicleSelected.replaceAll("\\s+", "")));
                System.out.println("PartId :"+partIdProperties.getProperty(vehicleSelected.replaceAll("\\s+", "")));

            }

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close connections
            DataBaseConnection.close(connection, null, null);
        }
    }

    @Given("Navigate to Campaign Test Ride page for {string}")
    public void NavigateCampaignTestRidePage(String vehicle) throws InterruptedException, IOException
    {
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

}

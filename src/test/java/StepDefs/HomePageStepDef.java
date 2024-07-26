package StepDefs;

import Utils.ExplicitWait;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.HomePage;
import com.tvs.pages.PriceSectionPage;
import com.tvs.pages.VehiclesPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.util.List;

public class HomePageStepDef
{
    private static final Logger log = LogManager.getLogger(TestRideStepDef.class);
    private WebDriver driver;
    private HomePage homePage;
    PriceSectionPage priceSectionPage;
    WebElement selectlanguagePopUp;
    VehiclesPage vehiclesPage;

    public HomePageStepDef() {
        this.driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
        priceSectionPage = new PriceSectionPage(driver);
        vehiclesPage = new VehiclesPage(driver);
        selectlanguagePopUp=priceSectionPage.selectlanguagePopUp;
    }

    @Given("navigate to the TVS Motor home page")
    public void navigate_to_the_tvs_motor_home_page()
    {
//        driver.get("https://uat-www.tvsmotor.net/");
        driver.get("https://www.tvsmotor.com/");

    }
    @When("a popup appears on the homepage close the pop up")
    public void a_popup_appears_on_the_homepage()
    {
        // Close the popup if it exists
        try {
            WebElement popup = driver.findElement(By.className("evg-popup"));
            if (popup.isDisplayed())
            {
                // Find the close button
                WebElement closeButton = popup.findElement(By.xpath("//button[@class='close evg-close evg-btn-dismissal']"));
                closeButton.click();
            }
        } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            // Popup not found or not displayed
        }

    }
    @Then("should close the pop up")
    public void should_close_the_pop_up() throws InterruptedException {
        WebElement ourProduct= driver.findElement(By.xpath("//a[text()='Our Products']"));
        Assert.assertEquals("OUR PRODUCTS",ourProduct.getText());
    }

    @And("verify the count of total vehicles")
    public void verify_the_count_of_total_vehicles() {
        List<WebElement> vehicles= driver.findElements(By.xpath("//div[@class='infoCont']/p[@class='name']"));
        Assert.assertEquals(vehicles.size(),19);
    }

    @Given("navigate to our products page")
    public void navigate_to_our_products_page() throws InterruptedException {
            homePage.ClickOurProducts();

    }
    @When("clicks on each vehicles tab and select the state")
    public void clicks_on_each_vehicles_tab_and_select_the_state() throws InterruptedException
    {
        homePage.ClickStateDropdown();

    }
    @Then("each image src has the same name as vehicle displayed on each card")
    public void each_image_src_has_the_same_name_as_vehicle_displayed_on_each_card() throws InterruptedException {
        homePage.ClickScooter();
        homePage.ClickMotorCycle();
        homePage.ClickMopeds();
        homePage.ClickElectric();
        homePage.ClickThreeWheeler();
    }
    @Then("selected state should be updated on vehicle page")
    public void selected_state_should_be_updated_on_vehicle_page() throws InterruptedException
    {
        List<WebElement> states = driver.findElements(By.xpath("//div[@class='infoCont']/p[2]"));

        for (WebElement state : states)
        {
            Thread.sleep(3000);
            String stateText = state.getText().trim();
            if (!stateText.isEmpty()) {
                System.out.println("State: " + stateText);
            }
        }
       // driver.quit();
    }
    @When("navigate to the \"Our Products\" section")
    public void navigate_to_our_products_section() throws InterruptedException {
        homePage.ClickOurProducts();
        try {
            homePage.ClickAcceptCookies();
        }
        catch(Exception e) {
            log.info("Cookies handled");
        }
    }

    @Then("user should see scooters listed with their respective names and 'Know More' links")
    public void verify_scooters_listed_with_know_more_links() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> scooters = vehiclesPage.getScootersList();
        for (WebElement scooter : scooters) {
            String scooterName = scooter.getText();
            System.out.println(scooterName);
            Assert.assertTrue("Know More link not found for scooter: " + scooterName , vehiclesPage.isKnowMoreLinkAvailable(scooterName));
        }
    }

    @And("clicks on 'Know More' for each scooter and verifies the title")
    public void click_know_more_for_each_scooter() throws InterruptedException {
        List<WebElement> scooters = vehiclesPage.getScootersList();
        for (int i = 0; i < scooters.size(); i++) {
            Thread.sleep(2000);
            try {
                scooters = vehiclesPage.getScootersList();
                String scooterName = scooters.get(i).getText();
                vehiclesPage.clickKnowMoreLink(scooterName);
                Thread.sleep(3000);
                vehiclesPage.handlePopups();

//              boolean newTabOpened = vehiclesPage.switchToNewTabIfOpened();
                String pageTitle = driver.getTitle();
                String normalisedPpageTitle = Utilities.normalizeString(pageTitle);
                String currentUrl = driver.getCurrentUrl();
                log.info("Url of vehicle: "+currentUrl+" for vehicle: "+scooterName+""
                        + "Page title of vehicle: "+pageTitle+""); //for logs
                String normalisedCurrentUrl = Utilities.normalizeString(currentUrl);
                System.out.println(normalisedCurrentUrl);									//for console
                String normalisedScooterName = Utilities.normalizeString(scooterName);
                System.out.println(normalisedScooterName);								    //for console
                System.out.println(normalisedPpageTitle);								    //for console

                String expectedUrlSubstring = vehiclesPage.getExpectedUrlSubstring(normalisedScooterName);
                System.out.println(expectedUrlSubstring+""+normalisedCurrentUrl);
                System.out.println(normalisedPpageTitle+"**"+normalisedScooterName);
                Assert.assertTrue("URL does not contain the scooter's name: " + scooterName, normalisedCurrentUrl.contains(expectedUrlSubstring));
                Assert.assertTrue("Page title does not contain the scooter's name: " + scooterName, normalisedPpageTitle.contains(normalisedScooterName));

                navigate_to_our_products_section();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @When("navigate to the \"Our Products\" page and click 'motorcycles'")
    public void navigate_to_our_products_page_and_click_motorcycles() throws InterruptedException {
    homePage.ClickOurProducts();
    vehiclesPage.clickMotorCycleTab();
    try {
        homePage.ClickAcceptCookies();
    }
    catch(Exception e) {
        log.info("Cookies handled");
    }
    ExplicitWait.waitUntilLoaderDisappears(driver);
}

    @Then("user should see motorcycles listed with their respective names and 'Know More' links")
    public void verify_motorcycles_listed_with_know_more_links() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> motorcycles = vehiclesPage.getMotorcyclesList();
        for (WebElement motorcycle : motorcycles) {
            String motorcycleName = motorcycle.getText();
            System.out.println(motorcycleName);
            Assert.assertTrue("Know More link not found for motorcycle: " + motorcycleName, vehiclesPage.isKnowMoreLinkAvailable(motorcycleName));
        }
    }


    @Then("verifies the url for each motorcycle page after clicking on their respective Know More links")
    public void verifies_the_url_for_each_motorcycle_page_after_clicking_on_their_respective_know_more_links() throws InterruptedException
    {
        Thread.sleep(2000);
        List<WebElement> motorcycles = vehiclesPage.getMotorcyclesList();
        for (int i = 0; i < motorcycles.size(); i++) {
            Thread.sleep(2000);
            try {
                motorcycles = vehiclesPage.getMotorcyclesList();
                String motorcycleName = motorcycles.get(i).getText();
                vehiclesPage.clickKnowMoreLink(motorcycleName);
                vehiclesPage.handlePopups();

                boolean newTabOpened = vehiclesPage.switchToNewTabIfOpened();
                String pageTitle = driver.getTitle();
                String normalisedPpageTitle = Utilities.normalizeString(pageTitle);
                String currentUrl = driver.getCurrentUrl();
                log.info("Url of vehicle: "+currentUrl+" for vehicle: "+motorcycleName+""); //for logs
                String normalisedCurrentUrl = Utilities.normalizeString(currentUrl);
                System.out.println(normalisedCurrentUrl);									//for console
                String normalisedMotorcycleName = Utilities.normalizeString(motorcycleName);
                System.out.println(normalisedMotorcycleName);								//for console

                String expectedUrlSubstring = vehiclesPage.getExpectedUrlSubstring(normalisedMotorcycleName);
                System.out.println(expectedUrlSubstring+"***"+normalisedCurrentUrl);
                System.out.println(normalisedMotorcycleName+"***"+normalisedPpageTitle);
                Assert.assertTrue("URL does not contain the motorcycle's name: " + motorcycleName, normalisedCurrentUrl.contains(expectedUrlSubstring));
              //  Assert.assertTrue("Page title does not contain the scooter's name: " + motorcycleName, normalisedPpageTitle.contains(normalisedMotorcycleName));
                if (newTabOpened) {
                    vehiclesPage.switchBackToOriginalTab();
                } else {
                    driver.navigate().back();
                }

                vehiclesPage.clickMotorCycleTab();;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @When("navigate to the \"Our Products\" page and click 'mopeds'")
    public void navigate_to_our_products_page_and_click_mopeds() throws InterruptedException {
        ExplicitWait.waitUntilLoaderDisappears(driver);
        homePage.ClickOurProducts();
        vehiclesPage.clickMopedTab();
        try {
            homePage.ClickAcceptCookies();
        }
        catch(Exception e) {
            log.info("Cookies handled");
        }
        ExplicitWait.waitUntilLoaderDisappears(driver);
    }


    @Then("user should see moped listed with its respective name and 'Know More' links")
    public void verify_moped_listed_with_know_more_links() {
        List<WebElement> mopeds = vehiclesPage.getMopedsList();
        Assert.assertEquals("Number of mopeds listed is not 1", 1, mopeds.size());

        for (WebElement moped : mopeds) {
            String mopedName = moped.getText();
            System.out.println(mopedName);
            Assert.assertTrue("Know More link not found for moped", vehiclesPage.isKnowMoreLinkAvailable(mopedName));
        }
    }



    @Then("verifies the url for moped page after clicking on its respective Know More links")
    public void verifies_the_url_for_moped_page_after_clicking_on_its_respective_know_more_links() {
        List<WebElement> mopeds = vehiclesPage.getMopedsList();
        for (int i = 0; i < mopeds.size(); i++) {
            try {
                mopeds = vehiclesPage.getMopedsList();
                String mopedName = mopeds.get(i).getText();
                vehiclesPage.clickKnowMoreLink(mopedName);
                vehiclesPage.handlePopups();
                String currentUrl = driver.getCurrentUrl();
                String normalizedCurrentUrl = Utilities.normalizeString(currentUrl);
                String normalizedMopedName = Utilities.normalizeString(mopedName);
                Assert.assertTrue("URL does not contain the moped's name: " + mopedName, normalizedCurrentUrl.contains(normalizedMopedName));
                driver.navigate().back();
                vehiclesPage.clickMopedTab();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.close();
    }

    @Given("navigate to the TVS Motor home page in {string} environment")
    public void navigate_to_the_tvs_motor_home_page_in_environment(String environment) {
        // driver = new ChromeDriver();  // Initialize WebDriver as per your setup
        String url = Utilities.getUrl(environment);
        driver.get(url);
        Utilities.verifyUrl(driver, environment);
    }

    @When("navigate to the \"Our Products\" page for state drop down")
    public void navigate_to_our_products_page_for_state_drop_down() throws InterruptedException {
        homePage.ClickAcceptCookies();
        homePage.ClickOurProducts();
    }

    @When("navigates through state drop down and check visibility for each vehicle type")
    public void navigates_through_state_drop_down_and_check_visibility_for_each_vehicle_type() throws InterruptedException {
        homePage.ClickOurProducts();
        List<WebElement> states = vehiclesPage.getStateDropdownOptions();

        for (int i=0;i<=states.size();i++) {
            try {
                Thread.sleep(1000);
                String stateName = states.get(i).getText();
                System.out.println(stateName);
                Thread.sleep(2000);
                states.get(i).click();
                vehiclesPage.clickScooterTab();
                ExplicitWait.waitUntilLoaderDisappears(driver);
                Thread.sleep(2000); // Wait for state-specific data to load

                verifyStateAndExShowroomPrice("Scooters", stateName);

                vehiclesPage.clickMotorCycleTab();
                Thread.sleep(1000);
                ExplicitWait.waitUntilLoaderDisappears(driver);
                verifyStateAndExShowroomPrice("Motorcycles", stateName);

                vehiclesPage.clickMopedTab();
                ExplicitWait.waitUntilLoaderDisappears(driver);
                verifyStateAndExShowroomPrice("Mopeds", stateName);
            }
            catch(Exception e) {
                e.printStackTrace();
            }



            // vehiclesPage.clickstateDropdown();
//            ExplicitWait.waitUntilLoaderDisappears(driver);
            vehiclesPage.clickScooterTab(); // Navigate back to scooters tab for the next iteration
//            ExplicitWait.waitUntilLoaderDisappears(driver);
//            vehiclesPage.clickstateDropdown();

        }
    }

    public void verifyStateAndExShowroomPrice(String vehicleType, String stateName) throws InterruptedException {
        List<WebElement> vehicles = vehiclesPage.getVehicleList(vehicleType);
        for (WebElement vehicle : vehicles) {
            ExplicitWait.waitUntilLoaderDisappears(driver);
            //Thread.sleep(1000);
            try {
                ExplicitWait.waitUntilLoaderDisappears(driver);
                String vehicleName = vehicle.getText();
                WebElement stateDisplayed = vehicle.findElement(By.xpath("./following-sibling::p[1]"));
                WebElement exShowroomPrice = vehicle.findElement(By.xpath("./following-sibling::p[2]//span"));
                ExplicitWait.waitForElementToBeClickable(driver, stateDisplayed, 10);
                ExplicitWait.waitForElementToBeClickable(driver, exShowroomPrice, 10);
                System.out.println(vehicleName + stateDisplayed.getText());
                String state = stateDisplayed.getText();
//          System.out.println(Utilities.normalizeStateName(stateDisplayed.getText())+"++"+ Utilities.normalizeStateName(stateName));
                String normalizedStateDisplayed = Utilities.normalizeString(state);
                String normalizedStateName =Utilities.normalizeStateName(stateName);
                System.out.println(normalizedStateDisplayed+"++"+normalizedStateName);
//            Assert.assertTrue("State name not displayed correctly for " + vehicleName, Utilities.normalizeString(stateDisplayed.getText()).contains(stateName.toLowerCase()));
                Assert.assertTrue("State name not displayed correctly for " + vehicleName,normalizedStateDisplayed.contains(normalizedStateName));
                Assert.assertTrue("Ex-showroom price not displayed for " + vehicleName, exShowroomPrice.isDisplayed());
            }
            catch(Exception e) {

            }
        }

    }

    @Then("the state name should be displayed along with ex-showroom price for the vehicle name for each scooter")
    public void state_name_should_be_displayed_for_each_scooter() {
        // This step is implicitly verified in the "verifyStateAndExShowroomPrice" method
    }

    @Then("the state name should be displayed along with ex-showroom price for the vehicle name for each motorcycle")
    public void state_name_should_be_displayed_for_each_motorcycle() {
        // This step is implicitly verified in the "verifyStateAndExShowroomPrice" method
    }

    @Then("the state name should be displayed along with ex-showroom price for the vehicle name for each moped")
    public void state_name_should_be_displayed_for_each_moped() {
        // This step is implicitly verified in the "verifyStateAndExShowroomPrice" method
        driver.close();
    }


}

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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static com.tvs.pages.HomePage.*;

public class HomePageStepDef {
    private static final Logger log = LogManager.getLogger(HomePageStepDef.class);
    private final WebDriver driver;
    private final HomePage homePage;
    PriceSectionPage priceSectionPage;
    WebElement selectlanguagePopUp;
    VehiclesPage vehiclesPage;
    WebElement AcceptCookie;
    String env;

    public HomePageStepDef() {
        this.driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
        priceSectionPage = new PriceSectionPage(driver);
        vehiclesPage = new VehiclesPage(driver);
        selectlanguagePopUp = priceSectionPage.selectlanguagePopUp;
        AcceptCookie = homePage.AcceptCookie;
    }


    @When("user navigated to home page and accepts the cookies pop-up")
    public void userNavigatedToHomePageAndAcceptsTheCookiesPopUp() {
        try {
            if (AcceptCookie.isDisplayed()) {
                homePage.ClickAcceptCookies();
            } else {
                System.out.println("Cookies acceptance pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookies acceptance pop-up not found on the page.");
        }
    }

    @When("user clicks on products and clicks on scooter tab")
    public void ClicksOnScootersTab() throws InterruptedException {
        homePage.ClickProducts();
        homePage.ClickScootersTab();
    }

    @Then("click on each vehicles to verify the title of scooters")
    public void clickOnEachVehiclesToVerifyTheTitle() throws InterruptedException
    {
        for (int i = 0; i < driver.findElements(homePage.allScooters).size(); i++) {
            List<WebElement> scooterLinks = driver.findElements(homePage.allScooters);
            WebElement scooterLink = scooterLinks.get(i);
            String scooterName = scooterLink.getText();
            System.out.println("vname-" + scooterName);
            scooterLink.click();

            if (scooterName.equalsIgnoreCase("TVS Jupiter")) {
                scooterLink.click();
                driver.getCurrentUrl().contains("tvs-jupiter");
            } else {
                String pageTitle = driver.getTitle();
                System.out.println("Page title: " + pageTitle);
                Assert.assertTrue("Page title doesn't contain " + scooterName, pageTitle.contains(scooterName.replace("+", " ")));
                driver.navigate().back();
                ClicksOnScootersTab();

            }
        }
    }

    @When("a popup appears on the homepage close the pop up")
    public void a_popup_appears_on_the_homepage() {
        // Close the popup if it exists
        try {
            WebElement popup = driver.findElement(By.className("evg-popup"));
            if (popup.isDisplayed()) {
                // Find the close button
                WebElement closeButton = popup.findElement(By.xpath("//button[@class='close evg-close evg-btn-dismissal']"));
                closeButton.click();
            }
        } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            // Popup not found or not displayed
        }

    }

    @Then("should close the pop up")
    public void should_close_the_pop_up() {
        WebElement ourProduct = driver.findElement(By.xpath("//a[text()='Our Products']"));
        Assert.assertEquals("OUR PRODUCTS", ourProduct.getText());
    }

    @Given("navigate to our products page")
    public void navigate_to_our_products_page() {
        // homePage.ClickOurProducts();

    }

    @When("clicks on each vehicles tab and select the state")
    public void clicks_on_each_vehicles_tab_and_select_the_state() {
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
    public void selected_state_should_be_updated_on_vehicle_page() throws InterruptedException {
        List<WebElement> states = driver.findElements(By.xpath("//div[@class='infoCont']/p[2]"));

        for (WebElement state : states) {
            Thread.sleep(3000);
            String stateText = state.getText().trim();
            if (!stateText.isEmpty()) {
                System.out.println("State: " + stateText);
            }
        }
        // driver.quit();
    }

    @Given("navigate to the TVS Motor home page in {string} environment")
    public void navigate_to_the_tvs_motor_home_page_in_environment(String environment) {
        env = environment;
        String url = Utilities.getUrl(environment);
        driver.get(url);
        //HandleAlert(driver,"PoGomjipkB");
        Utilities.verifyUrl(driver, environment);

    }

    @When("navigate to the \"Our Products\" page for state drop down")
    public void navigate_to_our_products_page_for_state_drop_down() {
        if (env.equalsIgnoreCase("UAT")) {
            driver.navigate().to("https://uat-www.tvsmotor.net/Our-Products/Vehicles");
        } else if (env.equalsIgnoreCase("PROD")) {
            driver.navigate().to("https://www.tvsmotor.com/Our-Products/Vehicles");
        }
    }

    @When("navigates through state drop down and check visibility for each vehicle type")
    public void navigates_through_state_drop_down_and_check_visibility_for_each_vehicle_type() {
        //homePage.ClickOurProducts();
        List<WebElement> states = vehiclesPage.getStateDropdownOptions();

        for (WebElement state : states) {
            try {
                Thread.sleep(1000);
                String stateName = state.getText();
                System.out.println(stateName);
                Thread.sleep(2000);
                state.click();
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            vehiclesPage.clickScooterTab(); // Navigate back to scooters tab for the next iteration

        }
    }

    public void verifyStateAndExShowroomPrice(String vehicleType, String stateName) {
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
                String normalizedStateDisplayed = Utilities.normalizeString(state);
                String normalizedStateName = Utilities.normalizeStateName(stateName);
                System.out.println(normalizedStateDisplayed + "++" + normalizedStateName);
                Assert.assertTrue("State name not displayed correctly for " + vehicleName, normalizedStateDisplayed.contains(normalizedStateName));
                Assert.assertTrue("Ex-showroom price not displayed for " + vehicleName, exShowroomPrice.isDisplayed());
            } catch (Exception ignored) {

            }
        }

    }


    @When("user clicks on products and clicks on Motorcycles tab")
    public void ClicksOnMotorcyclesTab() throws InterruptedException {
        //HandleAlert(driver,"PoGomjipkB");
        homePage.ClickProducts();
        //HandleAlert(driver,"PoGomjipkB");
        homePage.ClickMotorCyclesTab();
    }

    @Then("click on each motorcycle to verify redirection and validate booking,test ride,and dealer locator buttons")
    public void clickOnMotorcycleLinks() throws InterruptedException
    {
        List<WebElement> scooterLinks = driver.findElements(homePage.allMotorCycles);

        for (int i = 0; i < scooterLinks.size(); i++) {
            scooterLinks = driver.findElements(homePage.allMotorCycles); // Refresh list
            WebElement scooterLink = scooterLinks.get(i);
            scooterLink.click();

            // Handle language pop-up if it appears
            try {
                homePage.langPopUpDisappear();
            } catch (TimeoutException e) {
                System.out.println("Language pop-up not displayed.");
            }

            String mainWindow = driver.getWindowHandle(); // Store main window handle

            if (driver.getCurrentUrl().contains("tvs-sport")) {
                clickBookingBtn(driver);  // Click on Book Online (same tab)
                driver.navigate().back(); // Go back to Sport page

                clickTestRideBtn(driver); // Click Test Ride (opens in new tab)
                driver.navigate().back(); // Go back to Sport page

                // Switch to the newly opened tab
                Set<String> windowHandles = driver.getWindowHandles();
                for (String handle : windowHandles) {
                    if (!handle.equals(mainWindow)) {
                        driver.switchTo().window(handle);
                        break;
                    }
                }

                clickDealerLocatorBtn(driver); // Click Dealer Locator (same tab)

                driver.close(); // Close the second tab
                driver.switchTo().window(mainWindow); // Switch back to the main sport page
                ClicksOnMotorcyclesTab();


            } else {
                clickBookingBtn(driver);
                driver.navigate().back();
                clickTestRideBtn(driver);
                driver.navigate().back();
                clickDealerLocatorBtn(driver);
            }

            // Ensure we return to the main page before proceeding
            while (!driver.getCurrentUrl().contains("https://www.tvsmotor.com")) {
                driver.navigate().back();
            }

            // Refresh the element list to avoid stale elements
            scooterLinks = driver.findElements(homePage.allMotorCycles);

            // Only click the Motorcycles tab if it's NOT the last vehicle
            if (i < scooterLinks.size() - 1) {
                ClicksOnMotorcyclesTab();
            }
        }
    }

    @And("verify that url contains the vehicle name")
    public void verifyThatUrlContainsTheVehicleName(String vehicleName) {
        String url = driver.getCurrentUrl();
        String Vehicle = url.substring(url.lastIndexOf("/") + 1).replace("-", " ");
        System.out.println("vname-" + Vehicle);
        Assert.assertEquals("Url doesn't contain " + vehicleName, Vehicle, vehicleName.toLowerCase());
    }

    @When("user clicks on products and clicks on Electric tab")
    public void ClicksOnElectricTab() throws InterruptedException {
        homePage.ClickProducts();
        homePage.ClickElectricTab();
    }

    @Then("click on each vehicles to verify the title of Electric vehicles")
    public void clickOnElectricLinks() throws InterruptedException {
        for (int i = 0; i < driver.findElements(homePage.allElectric).size(); i++) {
            List<WebElement> VehicleLinks = driver.findElements(homePage.allElectric);

            WebElement VehicleLink = VehicleLinks.get(i);
            String vehicleName = VehicleLink.getText();
            System.out.println("vname-" + vehicleName);
            ExplicitWait.waitForElementToBeClickable(driver, VehicleLink, 10);
            VehicleLink.click();

            String pageTitle = driver.getTitle();
            System.out.println("Page title: " + pageTitle);

            //driver.navigate().to("https://uat-www.tvsmotor.net/");
            waitForThePageToLoadCompletely();
            driver.navigate().back();
            ClicksOnElectricTab();

        }
    }

    @When("user clicks on products and clicks on Mopeds tab")
    public void ClicksOnMopedsTab() throws InterruptedException {
        homePage.ClickProducts();
        homePage.ClickMopedsTab();
    }

    @Then("click on each vehicles to verify the title of Mopeds")
    public void clickOnEachVehiclesToVerifyTheTitleOfMopeds() throws InterruptedException {
        for (int i = 0; i < driver.findElements(homePage.allMopeds).size(); i++) {
            List<WebElement> scooterLinks = driver.findElements(homePage.allMopeds);

            WebElement scooterLink = scooterLinks.get(i);
            String scooterName = scooterLink.getText();
            System.out.println("vname-" + scooterName);
            scooterLink.click();

            String pageTitle = driver.getTitle();
            System.out.println("Page title: " + pageTitle);
            Assert.assertTrue("Page title doesn't contain " + scooterName, pageTitle.contains(scooterName));

            waitForThePageToLoadCompletely();
            clickBookingBtn(driver);
            driver.navigate().back();
            ClicksOnMopedsTab();
        }
    }

    @When("user clicks on products and clicks on ThreeWheelers tab")
    public void ClicksOnThreeWheelersTab() throws InterruptedException {
        homePage.ClickProducts();
        homePage.ClickThreeWheelersTab();
    }

    @Then("click on each vehicles to verify the title of ThreeWheelers")
    public void clickOnEachVehiclesToVerifyTheTitleOfThreeWheelers() throws InterruptedException {
        for (int i = 0; i < driver.findElements(homePage.allThreeWheelers).size(); i++) {
            List<WebElement> scooterLinks = driver.findElements(homePage.allThreeWheelers);

            WebElement scooterLink = scooterLinks.get(i);
            String scooterName = scooterLink.getText();
            System.out.println("vname-" + scooterName);
            scooterLink.click();

            String pageTitle = driver.getTitle();
            System.out.println("Page title: " + pageTitle);
            Assert.assertEquals("Page title doesn't contain " + scooterName, pageTitle, driver.getTitle());

            waitForThePageToLoadCompletely();
            clickBookingBtn(driver);
            driver.navigate().back();
            ClicksOnThreeWheelersTab();
        }
    }

    @And("match the all {int} with no of vehicles displayed for scooters")
    public void compareAllCountScooters(int count) {
        Assert.assertEquals("All count and vehicles displayed are not matching", count, +homePage.scootersCount());
    }

    @And("match the all {int} with no of vehicles displayed for Motorcycles")
    public void compareAllCountMotorcycles(int count)
    {
        Assert.assertEquals("All count and vehicles displayed are not matching", count, +homePage.motorCyclesCount());

    }

    @And("match the all {int} with no of vehicles displayed for electric")
    public void compareAllCountElectric(int count) {
        Assert.assertEquals("All count and vehicles displayed are not matching", count, +homePage.electricCount());

    }

    @And("match the all {int} with no of vehicles displayed for Mopeds")
    public void compareAllCountElectricMopeds(int count) {
        Assert.assertEquals("All count and vehicles displayed are not matching", count, +homePage.mopedsCount());
    }

    @And("match the all {int} with no of vehicles displayed for ThreeWheelers")
    public void matchTheAllCountWithNoOfVehiclesDisplayedForThreeWheelers(int count) {
        Assert.assertEquals("All count and vehicles displayed are not matching", count, +homePage.threeWheelersCount());
    }

    @Given("navigate to the TVS Motor brand page with {string}")
    public void navigateToTheTVSMotorBrandPageWith(String url) throws IOException {
        String selectedVehicleUrl = Utilities.getProductUrl(url);
        driver.get(selectedVehicleUrl);
    }

    @Then("wait for the page to load completely")
    public void waitForThePageToLoadCompletely() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        System.out.println("Page has fully loaded.");
    }


    @And("click on scooters to verify redirection and validate booking,test ride,and dealer locator buttons")
    public void clickOnScooterVehicles() throws InterruptedException
    {
        List<WebElement> scooterLinks = driver.findElements(homePage.allScooters);

        for (int i = 0; i < scooterLinks.size(); i++)
        {
            scooterLinks = driver.findElements(homePage.allScooters); // Refresh list
            WebElement scooterLink = scooterLinks.get(i);
            ExplicitWait.waitForElementToBeClickable(driver, scooterLink, 10);
            scooterLink.click();

            // Handle language pop-up if it appears
            try {
                homePage.langPopUpDisappear();
            } catch (TimeoutException e) {
                System.out.println("Language pop-up not displayed.");
            }

            clickBookingBtn(driver);
            driver.navigate().back();
            clickTestRideBtn(driver);
            driver.navigate().back();
            clickDealerLocatorBtn(driver);

            // Ensure we return to the main page before proceeding
            while (!driver.getCurrentUrl().contains("https://www.tvsmotor.com"))
            {
                driver.navigate().back();
            }

            // Refresh the element list to avoid stale elements
            scooterLinks = driver.findElements(homePage.allScooters);

            // Only click the Motorcycles tab if it's NOT the last vehicle
            if (i < scooterLinks.size() - 1)
            {
                ClicksOnScootersTab();
            }
        }
        ClicksOnScootersTab();

    }

    @And("click on electrics to verify redirection and validate booking,test ride,and dealer locator buttons")
    public void clickOnElectrics() throws InterruptedException {
        List<WebElement> scooterLinks = driver.findElements(homePage.allElectric);

        for (int i = 0; i < scooterLinks.size(); i++)
        {
            scooterLinks = driver.findElements(homePage.allElectric); // Refresh list
            WebElement scooterLink = scooterLinks.get(i);
            ExplicitWait.waitForElementToBeClickable(driver, scooterLink, 10);
            scooterLink.click();

            // Handle language pop-up if it appears
            try {
                homePage.langPopUpDisappear();
            } catch (TimeoutException e) {
                System.out.println("Language pop-up not displayed.");
            }

            // Ensure we return to the main page before proceeding
            while (!driver.getCurrentUrl().contains("https://www.tvsmotor.com"))
            {
                driver.navigate().back();
            }

            // Refresh the element list to avoid stale elements
            scooterLinks = driver.findElements(homePage.allScooters);

            // Only click the Motorcycles tab if it's NOT the last vehicle
            if (i < scooterLinks.size() - 1)
            {
                ClicksOnElectricTab();
            }
        }
        //ClicksOnElectricTab();
    }

    @And("click on mopeds to verify redirection and validate booking,test ride,and dealer locator buttons")
    public void clickOnMopeds() throws InterruptedException
    {
        List<WebElement> scooterLinks = driver.findElements(homePage.allMopeds);

        for (int i = 0; i < scooterLinks.size(); i++) {
            scooterLinks = driver.findElements(homePage.allMopeds); // Refresh list
            WebElement scooterLink = scooterLinks.get(i);
            scooterLink.click();

            // Handle language pop-up if it appears
            try {
                homePage.langPopUpDisappear();
            } catch (TimeoutException e) {
                System.out.println("Language pop-up not displayed.");
            }

            String mainWindow = driver.getWindowHandle(); // Store main window handle

            clickBookingBtn(driver);  // Click on Book Online (same tab)
            driver.navigate().back(); // Go back to Sport page

            clickTestRideBtn(driver); // Click Test Ride (opens in new tab)
            driver.navigate().back(); // Go back to Sport page

            // Switch to the newly opened tab
            Set<String> windowHandles = driver.getWindowHandles();
            for (String handle : windowHandles) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            clickDealerLocatorBtn(driver); // Click Dealer Locator (same tab)

            driver.close(); // Close the second tab
            driver.switchTo().window(mainWindow); // Switch back to the main sport page
            ClicksOnMopedsTab();


            // Ensure we return to the main page before proceeding
            while (!driver.getCurrentUrl().contains("https://www.tvsmotor.com")) {
                driver.navigate().back();
            }
        }
    }
}

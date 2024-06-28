package StepDefs;

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
//        List<WebElement> scooters = driver.findElements(By.xpath("//div[@data-id='#set1']/descendant::p[@class='name']"));
////        Assert.assertEquals("Number of scooters listed is not 5", 5, scooters.size());
//
//        for (WebElement scooter : scooters) {
//            String scooterName = scooter.getText();
//            WebElement knowMoreLink = driver.findElement(By.xpath("//p[text()='"+scooterName+"']/../../../descendant::a"));
//            Assert.assertTrue("Know More link not found for scooter", knowMoreLink.isDisplayed());
//        }
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
                System.out.println(expectedUrlSubstring);
                Assert.assertTrue("URL does not contain the scooter's name: " + scooterName, normalisedCurrentUrl.contains(expectedUrlSubstring));
                Assert.assertTrue("Page title does not contain the scooter's name: " + scooterName, normalisedPpageTitle.contains(normalisedScooterName));
//                if (newTabOpened) {
//                    vehiclesPage.switchBackToOriginalTab();
//                } else {
//                    driver.navigate().back();
//                }
//                driver.navigate().back();
//                vehiclesPage.clickMotorCycleTab();;
                navigate_to_our_products_section();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.close();
    }
//    	 List<WebElement> scooters = driver.findElements(By.xpath("//div[@data-id='#set1']/descendant::p[@class='name']"));
//    	 for (int i = 0; i < scooters.size(); i++) {
//    	     try {
//    	            // Re-locate the scooters list to avoid stale element exception
//    	            scooters = driver.findElements(By.xpath("//div[@data-id='#set1']/descendant::p[@class='name']"));
//    	            String scooterName = scooters.get(i).getText();
//    	            WebElement knowMoreLink = driver.findElement(By.xpath("//p[text()='" + scooterName + "']/../../../descendant::a"));
//    	            knowMoreLink.click();
//
////        for (WebElement scooter : scooters) {
////        	String scooterName = scooter.getText();
////            WebElement knowMoreLink = driver.findElement(By.xpath("//p[text()='"+scooterName+"']/../../../descendant::a"));
////            knowMoreLink.click();
//
//    	            try {
//
//    	            	if (selectlanguagePopUp.isDisplayed()) {
//
//    	            		By languageSelector = priceSectionPage.getLanguageSelector("English");
//
//    	            		WebElement languageElement = driver.findElement(languageSelector);
//
//    	            		waitForElementToBeClickable(driver, languageElement, 10);
//
//    	            		languageElement.click();
//
//    	            	}
//
//    	            } catch (Exception e) {
//
//    	            	log.info("Language selection pop-up not found or handled.");
//
//            }
//
////       	 Utilities.scrollToView(driver, priceBtn);
////       	 priceBtn.click();
//
//       	try
//       	{
//       		Thread.sleep(Duration.ofSeconds(5));
//       	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//       	 WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Request A Call Back')]")));
////       	 //Changed codeS
//
//       	 RemoteWebElement popup=(RemoteWebElement)driver.findElement(By.xpath("//*[contains(text(),'Request A Call Back')]"));
//       	 if(popup.isDisplayed())
//       	 {
//       		 RemoteWebElement cancel=(RemoteWebElement)driver.findElement(By.xpath("//button[@class='close evg-close evg-btn-dismissal']"));
//       		 cancel.click();
//       	 }
//       	}
//       	catch(Exception e)
//       	{
//       		log.info("Pop-Up not displayed");
//
//       	}
//
//            // Verify the title contains the scooter's name
//            String pageTitle = driver.getTitle();
//            pageTitle = Utilities.normalizeString(pageTitle);
//            scooterName = Utilities.normalizeString(scooterName);
//            Assert.assertTrue("Page title does not contain the scooter's name: " + scooterName, pageTitle.contains(scooterName));
//
//            // Navigate back to the "Our Products" section
//
//				navigate_to_our_products_section();
//    	     }	catch(StaleElementReferenceException e) {
//    	    	 log.error("Stale element reference exception caught and handled: \" + e.getMessage()");
// 			     i--;
//    	     } catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//   }




}

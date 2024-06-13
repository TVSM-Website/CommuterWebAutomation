package StepDefs;

import Utils.WebDriverManager;
import com.tvs.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.util.List;

public class HomePageStepDef
{
    private WebDriver driver;
    private HomePage homePage;

    public HomePageStepDef() {
        this.driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
    }

    @Given("navigate to the TVS Motor home page")
    public void navigate_to_the_tvs_motor_home_page()
    {
        driver.get("https://uat-www.tvsmotor.net/");

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
        driver.quit();
    }




}

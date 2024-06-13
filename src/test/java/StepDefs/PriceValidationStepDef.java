package StepDefs;

import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static APIs.PriceAPI.OrpDetails;
import static Utils.ExplicitWait.*;
import static Utils.Utilities.properties;


public class PriceValidationStepDef
{
    private static final Logger log = LogManager.getLogger(TestRideStepDef.class);

    private final WebDriver driver;
    PriceSectionPage priceSectionPage;
    WebElement AcceptCookie;
    WebElement stateDropdown;
    By states;
    String SelectedVehicle;
    Map<String, String> uiPrices = new HashMap<>();

    String state;
    public PriceValidationStepDef()
    {
        this.driver = WebDriverManager.getDriver();
        priceSectionPage= new PriceSectionPage(driver);
        AcceptCookie=priceSectionPage.AcceptCookie;
        stateDropdown=priceSectionPage.stateDropdown;
        states=priceSectionPage.states;
    }
    @Given("navigate to the tvs brand {string} page")
    public void navigate_to_the_tvs_pages(String vehicle) throws IOException
    {
        SelectedVehicle=vehicle;
        FileInputStream fileInputStream = new FileInputStream("src/test/Resources/BrandPageUrl.properties");
        properties.load(fileInputStream);
        String selectedVehicle=properties.getProperty(SelectedVehicle);
        driver.get(selectedVehicle);

        //WebElement AcceptCookie=driver.findElement(By.xpath("//div[@class='cookie_but']/a"));


    }

    @When("user navigated to the price section and accept the cookies pop up")
    public void user_navigated_to_the_price_section()
    {
        if(AcceptCookie.isDisplayed())
        {
            priceSectionPage.ClickAcceptCookies();
        }
        else
        {
            log.info("Pop-Up not displayed");
        }
        waitForElementToBeClickable(driver,stateDropdown,10);

    }
    @When("click on the state dropdown and select the state")
    public void click_on_the_state_dropdown_and_select_the_state() throws InterruptedException {
        priceSectionPage.ClickStateDropdown();
        visibilityOfElementLocated(driver, states,10);

    }

    @Then("get the prices for ex-showroom for the variants")
    public void getThePricesForExShowroomForTheVariantsFrom() throws InterruptedException
    {
        List<WebElement> stateList = driver.findElements(states);//driver.findElements(By.xpath("//div[@id='bs-select-1']//ul/li/a/span"));

        for (int i = 0; i < stateList.size(); i++)
        {
            stateList = driver.findElements(states);
            System.out.println("states: " + stateList.get(i).getText());
            state=stateList.get(i).getText();
            Thread.sleep(1000);

            stateList.get(i).click();
            //Thread.sleep(2000);

            waitForLoaderToDisappear(driver, By.className("loader_ajax"),10);
            priceSectionPage.ClickOnRoadPrice();

            Thread.sleep(2000);
            waitForLoaderToDisappear(driver, By.className("loader_ajax"),10);

            priceSectionPage.ClickStateDropdown();
            //Keys escape = Keys.ESCAPE;
            //div[@id='on-road']/dl[@class='row']/dt[@class='col-xs-6']- onroad models
            //div[@id='on-road']/dl[@class='row']/dd[@class='col-xs-6 price'] - onroad prices
            //List<WebElement> models = driver.findElements(By.xpath("//div[@id='priceTable']/dl[@class='row']//dt[@class='col-xs-6 variant-ext']"));
            //List<WebElement> prices = driver.findElements(By.xpath("//div[@id='priceTable']/dl[@class='row']//dd[@class='col-xs-6 price']"));

            List<WebElement> OnRoadModels = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dt[@class='col-xs-6']"));
            List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dd[@class='col-xs-6 price']"));

            uiPrices.clear();
            for (int j = 0; j < OnRoadModels.size(); j++) {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().replace("*", "").replace("₹", "").replace(",", "").trim();
                //int price = Integer.parseInt(priceText);
                String finalPrice=priceText.replace(" ","");
                uiPrices.put(model, finalPrice);
            }

            System.out.println("uiprices -"+uiPrices);

            JsonPath json= new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
            //System.out.println("state-"+state);
            System.out.println("val-"+"stateCodes."+state);
            String stateCode=json.getString("stateCodes."+state.replace(" ",""));
            System.out.println("stateCode-"+stateCode);
            OrpDetails(SelectedVehicle.replace("_"," "),stateCode);
//            List<WebElement> model_price=driver.findElements(By.xpath("//div[@id='priceTable']/dl[@class='row']"));
//            for(WebElement mp:model_price)
//            {
//                System.out.println(mp.getText());
//            }

//            List<WebElement> models=driver.findElements(By.xpath("//div[@id='priceTable']/dl[@class='row']//dt[@class='col-xs-6 variant-ext']"));
//            for(WebElement model:models)
//            {
//                System.out.println("model: "+model.getText());
//            }
//
//            List<WebElement> prices=driver.findElements(By.xpath("//div[@id='priceTable']/dl[@class='row']//dd[@class='col-xs-6 price']"));
//            for(WebElement price:prices)
//            {
//                System.out.println("price: "+price.getText());
//            }
//            String Ex_ShowroomPrice=driver.findElement(By.xpath("//div[@id='priceTable']/dl[@class='row']//dd[@class='col-xs-6 price']")).getText();
//            System.out.println("price -"+Ex_ShowroomPrice);
//            div[@id='priceTable']/dl[@class='row']//dt[@class='col-xs-6 variant-ext']
//            div[@id='priceTable']/dl[@class='row']//dd[@class='col-xs-6 price']
        }
    }
}

package StepDefs.PriceStepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPages.JupiterPricePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static APIs.ExShowroomProdAPI.GetExshowroomPriceProd;
import static APIs.ExShowroomUATAPI.GetExshowroomPriceUAT;
import static Utils.ExplicitWait.visibilityOfElementLocated;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JupiterExPriceStepDef
{
    WebDriver driver;
    String state;
    String env;
    String SelectedVehicle;
    String selectedVehicleUrl;
    Response response;
    private List<WebElement> stateList;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;
    JupiterPricePage jupiterPricePage;
    public JupiterExPriceStepDef()
    {
        this.driver= WebDriverManager.getDriver();
        jupiterPricePage = new JupiterPricePage(driver);
    }
    private Map<String, Map<String, String>> readExcelPrices() throws IOException {
        return ExShowRoomExcelUtils.readExcelData("src/test/Resources/TestData/ORP_Data_PROD_26052025.xlsx", "Sheet1");
    }

    @Given("navigate to the given {string} page on {string}")
    public void navigateToTheGivenPageOn(String vehicle, String environment) throws IOException {
        env = environment;
        SelectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("user closes the location pop up")
    public void userClosesTheLocationPopUp() throws InterruptedException
    {
        Thread.sleep(2000);
        jupiterPricePage.closeLocationPopUp();
    }

    @Then("click on price button to navigate to price section")
    public void clickOnPriceButtonToNavigateToPriceSection() {
        jupiterPricePage.clickPriceButton();
    }

    @When("click on the jupiter state dropdown and fetch all states")
    public void clickOnTheJupiterStateDropdownAndFetchAllStates()
    {
        jupiterPricePage.ClickStateDropdown();
        visibilityOfElementLocated(driver, jupiterPricePage.states, 15);
    }

    @Then("fetch all states from the dropdown")
    public void fetchAllStatesFromTheDropdown()
    {
        stateList = driver.findElements(jupiterPricePage.states);
    }

    @Then("iterate through each state and get Ex-showRoom prices")
    public void iterateThroughEachStateAndGetExShowRoomPrices() throws InterruptedException, IOException {
        for (int i = 0; i < stateList.size(); i++) {
            stateList = driver.findElements(jupiterPricePage.states);
            state = stateList.get(i).getText();
            System.out.println("\nstate: " + state);

            if (!state.isEmpty()) {
                stateList.get(i).click();
            } else {
                break;
            }

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            if (i < stateList.size() - 1)
            {
                jupiterPricePage.ClickStateDropdown();
                Thread.sleep(2500);
            }
            jupiterPricePage.ClickStateDropdown();
            Thread.sleep(500);
            fetchExShowroomPrices();
            compareUIAndAPIPricesWithExcelPrices();
        }
    }

    @Then("fetch Ex-showroom prices for the state selected")
    public void fetchExShowroomPrices()
    {
        uiPrices.clear();
        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//ul[@id='priceid1']/li[position() > 1]/span[1]"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//ul[@id='priceid1']/li[position() > 1]/span[2]"));

        if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else {
            for (int j = 0; j < OnRoadModels.size(); j++)
            {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().trim();
                String price = priceText.replaceAll("[^0-9]", "");
                uiPrices.put(model, price);
            }
        }
    }

    @Then("compare UI and API prices with Excel prices for the variants and states")
    public void compareUIAndAPIPricesWithExcelPrices() throws InterruptedException, IOException
    {
        JsonPath json = new JsonPath(new File("src/test/Resources/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT"))
        {
            response = GetExshowroomPriceUAT(SelectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = GetExshowroomPriceProd(SelectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        Thread.sleep(1000);
        apiPrices = response.jsonPath().getList("");
        Map<String, Map<String, String>> excelPrices = readExcelPrices();

        for (Map<String, Object> apiPrice : apiPrices) {
            String variantName = (String) apiPrice.get("VariantNameExtension");
            String apiexShowroomPrice = (String) apiPrice.get("Price");

            if (uiPrices.containsKey(variantName))
            {
                int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + ExShowRoomExcelUtils.MappedStateName (state);

                if (key.contains("Chandigarh") || key.contains("Himachal Pradesh") != key.contains("TVS Radeon"))
                {
                    System.out.println("Comparing prices for model: " + variantName);
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice);
                    assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);
                }
                else
                {
                    long roundedExcelPrice = 0;
                    if (excelPrices.containsKey(key)) {
                        String excelExshowroomPrice = excelPrices.get(key).get("Ex-ShowRoomPrice");
                        roundedExcelPrice = Math.round(Float.parseFloat(excelExshowroomPrice));

                    } else {
                        System.out.println("Model " + variantName + " not found in Excel data");
                    }

                    // Print all prices together
                    System.out.println("Comparing prices for model: " + variantName);
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice + ", Excel Price: " + roundedExcelPrice);

                    // Assertions
                    assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);
                    assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, UIexShowRoomPrice);

                }
            } else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
        }
    }
}

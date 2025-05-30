package StepDefs.PriceStepDefs;

import Utils.ORPExcelUtils;
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

import static APIs.ORPProdAPI.OrpDetailsPROD;
import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ExplicitWait.visibilityOfElementLocated;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.ORPExcelUtils.MappedStateName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JupiterOrpStepDef
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
    public JupiterOrpStepDef()
    {
        this.driver = WebDriverManager.getDriver();
        jupiterPricePage = new JupiterPricePage(driver);

    }
    private Map<String, Map<String, String>> readExcelPrices() throws IOException {
        return ORPExcelUtils.readExcelData("src/test/Resources/TestData/ORP_Data_PROD_26052025.xlsx", "Sheet1");
    }

    @Given("navigate to the given {string} page on the {string}")
    public void navigateToTheGivenPageOnThe(String vehicle, String environment) throws IOException {
        env = environment;
        SelectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("click on the jupiter orp state dropdown and fetch all states")
    public void clickOnTheJupiterOrpStateDropdownAndFetchAllStates()
    {
        jupiterPricePage.ClickOnRoadPriceTab();
        jupiterPricePage.ClickStateDropdown();
        jupiterPricePage.closeBotPopUp();
        visibilityOfElementLocated(driver, jupiterPricePage.states, 15);
    }

    @Then("fetch all states from the orp dropdown")
    public void fetchAllStatesFromTheOrpDropdown()
    {
        stateList = driver.findElements(jupiterPricePage.states);
    }

    @Then("iterate through each state and get orp prices")
    public void iterateThroughEachStateAndGetOrpPrices() throws InterruptedException, IOException {
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
                jupiterPricePage.ClickOnRoadPriceTab();
                jupiterPricePage.ClickStateDropdown();
                Thread.sleep(2500);
            }
            jupiterPricePage.ClickStateDropdown();
            fetchOrpPrices();
            compareUIAndAPIOrpPricesWithExcelPrices();
        }

    }

    @Then("fetch orp prices for the state selected")
    public void fetchOrpPrices()
    {
        uiPrices.clear();
        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//ul[@id='priceid2']/li[position() > 1]/span[1]"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//ul[@id='priceid2']/li[position() > 1]/span[2]"));

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

    @Then("compare UI and API orp prices with Excel prices for the variants and states")
    public void compareUIAndAPIOrpPricesWithExcelPrices() throws IOException, InterruptedException {
        JsonPath json = new JsonPath(new File("src/test/Resources/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT")) {
            response = OrpDetailsUAT(SelectedVehicle.replace("_", " "), stateCode);
            //System.out.println("api resp: " + response.asString());
        } else if (env.equalsIgnoreCase("PROD")) {
            response = OrpDetailsPROD(SelectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        Thread.sleep(1000);
        apiPrices = response.jsonPath().getList("");
        Map<String, Map<String, String>> excelPrices = readExcelPrices();
        for (Map<String, Object> apiPrice : apiPrices)
        {
            String variantName = (String) apiPrice.get("VariantName");
            int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

            if (uiPrices.containsKey(variantName))
            {
                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
                long roundedExcelPrice = 0;
                if (excelPrices.containsKey(key)) {
                    double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
                    roundedExcelPrice = Math.round(excelOnRoadPrice);
                } else {
                    System.out.println("Model " + variantName + " not found in Excel data");
                }

                // Print all prices together
                System.out.println("Comparing prices for model: " + variantName);
                System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                // Assertions
                assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
            } else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
        }
    }
}

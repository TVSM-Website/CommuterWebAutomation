package StepDefs.PriceStepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPages.ExshowRoomPricePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static APIs.ExShowroomProdAPI.GetExshowroomPriceProd;
import static APIs.ExShowroomUATAPI.GetExshowroomPriceUAT;
import static Utils.ExplicitWait.*;
import static Utils.ORPExcelUtils.MappedStateName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExShowRoomPriceStepDef
{
    WebDriver driver;
    WebElement stateDropdown;
    By states;
    By statesRaider;
    String state;
    String env;
    String SelectedVehicle;
    String selectedVehicleUrl;
    Response response;
    String selectedVariant;
    private List<WebElement> stateList;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;
    ExshowRoomPricePage exshowRoomPricePage;

    private Map<String, Map<String, String>> readExcelPrices(String filePath) throws IOException {
        return ExShowRoomExcelUtils.readExcelData(filePath, "Sheet1");
    }

    Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/ORP_Data_PROD_13052025.xlsx");

    public ExShowRoomPriceStepDef() throws IOException {
        this.driver= WebDriverManager.getDriver();
        exshowRoomPricePage = new ExshowRoomPricePage(driver);
        stateDropdown = exshowRoomPricePage.stateDropdown;
        states= exshowRoomPricePage.states;
        statesRaider=exshowRoomPricePage.statesRaider;
    }

    @Given("navigate to the {string} brand page in {string}")
    public void navigateToTheBrandPageIn(String vehicle, String environment) throws IOException {
        env = environment;
        SelectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("click on the state dropdown and fetch the states")
    public void clickOnTheStateDropdownAndFetchTheStates()
    {
        exshowRoomPricePage.ClickStateDropdown();
        visibilityOfElementLocated(driver, states, 15);
    }

    @Then("get the list of all states for the selected vehicle")
    public void getTheListOfAllStatesForTheSelectedVehicle()
    {
        stateList = driver.findElements(exshowRoomPricePage.states);
        System.out.println("List of all states fetched successfully.");
    }

    @Then("iterate through each state to select and fetch Ex-showRoom prices")
    public void iterateThroughEachStateToSelectAndFetchExShowRoomPrices() throws IOException, InterruptedException {
        for (int i = 0; i < stateList.size(); i++) {
            stateList = driver.findElements(exshowRoomPricePage.states);
            state = stateList.get(i).getText();
            System.out.println("\nstate: " + state);

            if (!state.isEmpty()) {
                stateList.get(i).click();
            } else {
                break;
            }

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            if (i < stateList.size() - 1) {
                exshowRoomPricePage.ClickStateDropdown();
            }
            fetchExShowroomPricesForTheSelectedState();
            compareUIAPIExcelPrices();
        }
    }
    @Then("fetch Ex-showroom prices for the selected state")
    public void fetchExShowroomPricesForTheSelectedState()
    {
        uiPrices.clear();
        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//div[@id='ex-showroom']/div/dl[@class='row']/dt"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//div[@id='ex-showroom']/div/dl[@class='row']/dd/span"));

        if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else {
            for (int j = 0; j < OnRoadModels.size(); j++) {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().replace("*", "").replace("₹", "").replace(",", "").trim();
                uiPrices.put(model, priceText.replace(" ", ""));
            }
        }
    }

    @Then("compare UI and API prices with Excel prices for all variants in each state")
    public void compareUIAPIExcelPrices() throws IOException, InterruptedException {
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

        for (Map<String, Object> apiPrice : apiPrices) {
            String variantName = (String) apiPrice.get("VariantNameExtension");
            String apiexShowroomPrice = (String) apiPrice.get("Price");

            if (uiPrices.containsKey(variantName))
            {
                int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" +ExShowRoomExcelUtils.MappedStateName (state);

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

    @Then("get the Ex-showroom prices for all the states and variants for raider")
    public void  getTheExShowroomPricesForAllTheStatesAndVariantsForRaider() throws InterruptedException, IOException {

        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/SingleSeatORP.xlsx");

        for (int c = 0; c < 6; c++)
        {

            // Click the right arrow to navigate to the next variant
            if (c > 0) {
                exshowRoomPricePage.clickRightArrow();
                Thread.sleep(2000);
            }

            //visibilityOfElementLocated(driver, selectedVariant, 10);
            selectedVariant = driver.findElement(By.cssSelector("div[class='item active'] h4")).getText();

            //System.out.println("Selected Variant: " + selectedVariant);

            // wait for the state dropdown to be visible and click it
            visibilityOfElementLocated(driver, states, 15);
            exshowRoomPricePage.clickRaiderStateDropdown();

            List<WebElement> stateList = driver.findElements(statesRaider);
            for (int i = 0; i < stateList.size(); i++) {
                stateList = driver.findElements(exshowRoomPricePage.statesRaider);
                System.out.println("\n states: " + stateList.get(i).getText());
                state = stateList.get(i).getText();

                WebElement stateElement = stateList.get(i);
                if (!state.isEmpty()) {
                    stateElement.click();
                } else {
                    break;
                }

                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                //exshowRoomPricePage.ClickOnRoadPriceRaider();

                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

                if (i < stateList.size() - 1) {
                    exshowRoomPricePage.clickRaiderStateDropdown();
                }

                //Thread.sleep(2000);
                WebElement OnRoadModel = driver.findElement(By.cssSelector("div[class='item active'] h4"));
                WebElement OnRoadPrice = driver.findElement(By.xpath("//div[@class='item active']//i[@id='priceLable']"));

                uiPrices.clear();
                if (!OnRoadModel.isDisplayed() || !OnRoadPrice.isDisplayed() || OnRoadPrice.getText().equalsIgnoreCase("ORP not available")) {
                    System.out.println("No prices available in UI for state: " + state);
                } else {

                    String model = OnRoadModel.getText();
                    String priceText = OnRoadPrice.getText().replace("*", "").replace("₹", "").replace(",", "").trim();
                    String finalPrice = priceText.replace(" ", "");
                    uiPrices.put(model, finalPrice);
                }
                //System.out.println("uiPrices -" + uiPrices);
                JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
                String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
                System.out.println("selectedVehicle -" + SelectedVehicle);
                Response response = GetExshowroomPriceUAT(SelectedVehicle.replace("_", " "), stateCode);
                if (response.getStatusCode() == 204) {
                    System.out.println("No content in API response for state: " + state);
                    assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                    continue; // Skip to the next state if API call fails
                }
                String responseBody = response.getBody().asString();
                if (responseBody == null || responseBody.isEmpty() || response.getStatusCode() == 204) {
                    System.out.println("API response is empty for state: " + state);
                    assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                    continue; // Skip to the next state if API response is empty
                }
                try {
                    apiPrices = response.jsonPath().getList("");
                } catch (Exception e) {
                    System.out.println("Failed to parse API response for state: " + state);
                    continue; // Skip to the next state if parsing fails
                }

                if (uiPrices.isEmpty() && apiPrices.isEmpty()) {
                    System.out.println("Both UI and API have no prices for state: " + state);
                    Assert.assertTrue(String.valueOf(uiPrices.isEmpty()),apiPrices.isEmpty());
                    continue; // Both UI and API have no prices, skip to the next state
                }

                if (apiPrices.isEmpty()) {
                    System.out.println("No prices available in API for state: " + state);
                    assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                    continue; // Skip to the next state if no prices are found in API
                }
                // System.out.println("apiprice "+apiPrices);


                if(selectedVariant.equalsIgnoreCase("DRUM"))
                {
                    selectedVariant = selectedVariant.substring(0, 1).toUpperCase() + selectedVariant.substring(1).toLowerCase();

                    //System.out.println("drum_var: "+selectedVariant);
                    apiPrices = apiPrices.stream()
                            .filter(apiPrice -> selectedVariant.equals(apiPrice.get("VariantNameExtension")))
                            .collect(Collectors.toList());

                    //System.out.println("apiPrices -" + apiPrices);
                    for (Map<String, Object> apiPrice : apiPrices) {
                        String variantName = (String) apiPrice.get("VariantNameExtension");
                        //System.out.println("apiVariantName -" + variantName);
                        String apiexShowroomPrice = (String) apiPrice.get("Price");
                        //System.out.println("VarName"+variantName);
                        //System.out.println("apiPrice-"+apiexShowroomPrice);

                        //System.out.println("variantName -" + variantName);
                        // Handle cases where the UI does not have the variant present in the API
                        if (uiPrices.containsKey(variantName.toUpperCase())) {
                            System.out.println(uiPrices.containsKey(variantName));
                            int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName.toUpperCase()));
                            System.out.println("Comparing prices for model: " + variantName);
                            // Get Excel price
                            String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
                            if (key.contains("Chandigarh") || key.contains("Himachal Pradesh") != key.contains("TVS Radeon")) {
                                System.out.println("Comparing prices for model: " + variantName);
                                System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice);
                                assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);

                            } else
                            {
                                long roundedExcelPrice = 0;
                                if (excelPrices.containsKey(key)) {
                                    double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("Ex-ShowRoomPrice"));
                                    roundedExcelPrice = Math.round(excelOnRoadPrice);
                                } else {
                                    System.out.println("Model " + variantName + " not found in Excel data");
                                }

                                // Print all prices together
                                System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice + ", Excel Price: " + roundedExcelPrice);

                                // Assertions
                                assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);
                                assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, UIexShowRoomPrice, 0.0);
                            }
                        }
                        else {
                            System.out.println("Model " + variantName + " not found in UI data");
                        }
                    }
                }
                else {
                    apiPrices = apiPrices.stream()
                            .filter(apiPrice -> selectedVariant.equals(apiPrice.get("VariantNameExtension")))
                            .collect(Collectors.toList());
                    for (Map<String, Object> apiPrice : apiPrices)
                    {
                        String variantName = (String) apiPrice.get("VariantNameExtension");
                        //System.out.println("apiVariantName -" + variantName);
                        String apiExshowroomPrice = (String) apiPrice.get("Price");

                        //System.out.println("variantName -" + variantName);
                        // Handle cases where the UI does not have the variant present in the API
                        if (uiPrices.containsKey(variantName))
                        {
                            System.out.println(uiPrices.containsKey(variantName));
                            int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));
                            System.out.println("Comparing prices for model: " + variantName);
                            // Get Excel price
                            String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
                            System.out.println("key -" + key);
                            if (key.contains("Chandigarh") || key.contains("Himachal Pradesh") != key.contains("TVS Radeon")) {
                                System.out.println("Comparing prices for model: " + variantName);
                                System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiExshowroomPrice);
                                assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiExshowroomPrice), UIexShowRoomPrice);

                            }
                            else {
                                long roundedExcelPrice = 0;
                                if (excelPrices.containsKey(key))
                                {
                                    double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("Ex-ShowRoomPrice"));
                                    System.out.println("excelPrice -" + excelOnRoadPrice);
                                    roundedExcelPrice = Math.round(excelOnRoadPrice);
                                    System.out.println("roundedExcelPrice -" + roundedExcelPrice);
                                }
                                else {
                                    System.out.println("Model " + variantName + " not found in Excel data");
                                }

                                // Print all prices together
                                System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiExshowroomPrice + ", Excel Price: " + roundedExcelPrice);

                                // Assertions
                                //assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiExshowroomPrice), UIexShowRoomPrice);
                                assertEquals("Price mismatch for model: " + variantName + " with Excel", Integer.parseInt(String.valueOf(roundedExcelPrice)), UIexShowRoomPrice, 0.0);

                            }
                        }
                            else {
                            System.out.println("Model " + variantName + " not found in UI data");
                        }
                    }
                }
            }
        }
    }

}

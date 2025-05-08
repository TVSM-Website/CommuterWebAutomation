package StepDefs.PriceStepDefs;

import Utils.ORPExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPages.RaiderPricePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ExplicitWait.*;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.ORPExcelUtils.MappedStateNameRaider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RaiderOrpStepDef {
    private Map<String, Map<String, String>> readExcelPrices(String filePath, String sheetName) throws IOException {
        return ORPExcelUtils.readExcelData(filePath, sheetName);
    }

    private final WebDriver driver;
    RaiderPricePage priceSectionPage;
    WebElement AcceptCookie;
    WebElement stateDropdown;
    WebElement selectlanguagePopUp;
    By states;
    By statesRaider;
    String selectedVehicle;
    String selectedVehicleUrl;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;
    String env;
    Response response;
    String state;
    WebElement roninStateDropdown;
    WebElement raiderPriceSection;
    WebElement stateDropdownRaider;
    //By selectedVariant;
    String selectedVariant;
    private List<WebElement> stateList;

    public RaiderOrpStepDef() {
        this.driver = WebDriverManager.getDriver();
        priceSectionPage = new RaiderPricePage(driver);
        AcceptCookie = priceSectionPage.AcceptCookie;
        stateDropdown = priceSectionPage.stateDropdown;
        states = priceSectionPage.states;
        selectlanguagePopUp = priceSectionPage.selectlanguagePopUp;
        roninStateDropdown = priceSectionPage.roninStateDropdown;
        raiderPriceSection = priceSectionPage.raiderPriceSection;
        stateDropdownRaider = priceSectionPage.stateDropdownRaider;
        statesRaider = priceSectionPage.statesRaider;

    }

    @Given("user navigate to {string} brand page on {string}")
    public void userNavigateToBrandPageOn(String vehicle, String environment) throws IOException {
        env = environment;
        selectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);

        driver.get(selectedVehicleUrl);
        driver.navigate().refresh();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='95%';");

    }

    @Then("get the On-Road prices for all the states and variants {string}")
    public void getTheOnRoadPricesForAllTheStatesAndVariants(String variant) throws InterruptedException, IOException
    {
        priceSectionPage.closePopUp();
        Thread.sleep(1500);
        Utilities.scrollToElement(driver.findElement(By.xpath("//section[@class='price-section section-raider']")));
        Thread.sleep(1500);
        waitForElementToBeClickable(driver, stateDropdownRaider, 15);
        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/ORP_Data_Prod_16012025.xlsx", "Sheet1");
        boolean variantFound = false;
        for (int c = 0; c < 6; c++) { // Assuming a maximum of 6 variants in the slider
            String currentVariant = driver.findElement(By.cssSelector("div[class='item active'] h4")).getText();

            if (currentVariant.equalsIgnoreCase(variant))
            {
                variantFound = true;

                // Wait for the state dropdown to be visible
                priceSectionPage.ClickOnRoadPrice();
                priceSectionPage.clickRaiderStateDropdown();
                visibilityOfElementLocated(driver, states, 10);
                //System.out.println("states size: " + driver.findElements(states).size());

                List<WebElement> stateList = driver.findElements(states);
                for (int i = 0; i < stateList.size(); i++) {
                    stateList = driver.findElements(priceSectionPage.states);
                    System.out.println("\nState: " + stateList.get(i).getText());
                    state = stateList.get(i).getText();

                    WebElement stateElement = stateList.get(i);
                    if (!state.isEmpty()) {
                        stateElement.click();
                    } else {
                        break;
                    }

                    waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                    //priceSectionPage.ClickOnRoadPriceRaider();

                    waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

                    if (i < stateList.size() - 1) {
                        priceSectionPage.clickRaiderStateDropdown();
                    }

                    Thread.sleep(2000);
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

                    JsonPath json = new JsonPath(new File("src/test/Resources/RaiderStateCode.json"));
                    String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
                    //System.out.println("State code for " + state + ": " + stateCode);
                    Response response = OrpDetailsUAT(selectedVehicle.replace("_", " "), stateCode);

                    if (response.getStatusCode() == 204) {
                        System.out.println("No content in API response for state: " + state);
                        assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                        continue;
                    }

                    String responseBody = response.getBody().asString();
                    if (responseBody == null || responseBody.isEmpty() || response.getStatusCode() == 204) {
                        System.out.println("API response is empty for state: " + state);
                        assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                        continue;
                    }

                    try {
                        apiPrices = response.jsonPath().getList("");
                    } catch (Exception e) {
                        System.out.println("Failed to parse API response for state: " + state);
                        continue;
                    }

                    if (uiPrices.isEmpty() && apiPrices.isEmpty()) {
                        System.out.println("Both UI and API have no prices for state: " + state);
                        Assert.assertTrue(uiPrices.isEmpty());
                        continue;
                    }

                    if (apiPrices.isEmpty()) {
                        System.out.println("No prices available in API for state: " + state);
                        assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
                        continue;
                    }

                    apiPrices = apiPrices.stream()
                            .filter(apiPrice -> variant.equalsIgnoreCase((String) apiPrice.get("VariantName")))
                            .collect(Collectors.toList());

                    for (Map<String, Object> apiPrice : apiPrices) {
                        if (((String) apiPrice.get("VariantName")).equalsIgnoreCase("iGO"))
                        {
                            String variantName = ((String) apiPrice.get("VariantName"));

                            // Fetch the price from API
                            int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

                            if (uiPrices.containsKey(variantName)) {
                                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
                                System.out.println("Comparing prices for model: " + variantName);

                                // Normalize Excel key lookup
                                String excelKey = selectedVehicle.replace("_", " ") + "|" + apiPrice.get("VariantName") + "|" + MappedStateNameRaider(state);

                                if (excelKey.contains("Chandigarh") || excelKey.contains("Himachal Pradesh")) {
                                    System.out.println("Comparing prices for model: " + variantName);
                                    System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice);
                                    assertEquals("Price mismatch for model: " + variantName,apiOnRoadPrice, uiOnRoadPrice);
                                }
                                //System.out.println("Excel Key: " + excelKey);
                                else {

                                    long roundedExcelPrice = 0;
                                    if (excelPrices.containsKey(excelKey)) {
                                        double excelOnRoadPrice = Float.parseFloat(excelPrices.get(excelKey).get("OnRoadPrice"));
                                        roundedExcelPrice = Math.round(excelOnRoadPrice);
                                    } else {
                                        System.out.println("Model " + apiPrice.get("VariantName") + " not found in Excel data");
                                    }

                                    // Print all prices together
                                    System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                                    // Assertions
                                    assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                                    assertEquals("Price mismatch for model: " + apiPrice.get("VariantName") + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
                                }
                            } else {
                                System.out.println("Model " + variantName + " not found in UI data. Available models: " + uiPrices.keySet());
                            }
                        }
                        else
                        {
                            String variantName = ((String) apiPrice.get("VariantName")).toUpperCase();
                            int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");
                            // Handle cases where the UI does not have the variant present in the API
                            if (uiPrices.containsKey(variantName))
                            {
                                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
                                System.out.println("Comparing prices for model: " + variantName);
                                // Get Excel price
                                String key = selectedVehicle.replace("_", " ") + "|" + apiPrice.get("VariantName") + "|" + MappedStateNameRaider(state);
                                //System.out.println("Excel Key: " + key);
                                long roundedExcelPrice = 0;
                                if (excelPrices.containsKey(key))
                                {
                                    double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
                                    roundedExcelPrice = Math.round(excelOnRoadPrice);
                                } else {
                                    System.out.println("Model " + variantName + " not found in Excel data");
                                }

                                // Print all prices together
                                System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                                // Assertions
                                assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                                assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
                            }
                            else {
                                System.out.println("Model " + variantName + " not found in UI data");
                            }
                        }

                    }


                }
                break; // Stop processing other slider items once the specified variant is handled
            }

            // If the specified variant is not found yet, click the right arrow to navigate

            if (!variantFound) {
                priceSectionPage.clickRightArrow();
                Thread.sleep(2000);
            }
        }

        if (!variantFound) {
            throw new IllegalStateException("Variant " + variant + " not found in the slider.");
        }

    }
}


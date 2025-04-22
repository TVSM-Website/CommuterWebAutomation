package StepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.ORPExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPage;
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
import static APIs.ORPProdAPI.OrpDetailsPROD;
import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.ORPExcelUtils.MappedStateName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExPriceApacheStepDef
{
    private Map<String, Map<String, String>> readExcelPrices(String filePath, String sheetName) throws IOException {
        return ExShowRoomExcelUtils.readExcelData(filePath, sheetName);
    }

    private final WebDriver driver;
    PriceSectionPage priceSectionPage;
    WebElement selectlanguagePopUp;
    WebElement AcceptCookie;
    By apacheStates;
    WebElement apacheVariantDropdown;
    WebElement apacheStateDropdown;
    By ApacheVariants;
    String state;
    String env;
    Response response;
    String SelectedVehicle;
    String selectedVariant;
    String selectedVehicleUrl;
    private List<WebElement> stateList;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;

    public ExPriceApacheStepDef() throws IOException {
        this.driver = WebDriverManager.getDriver();
        priceSectionPage = new PriceSectionPage(driver);
        AcceptCookie = priceSectionPage.AcceptCookie;
        selectlanguagePopUp = priceSectionPage.selectlanguagePopUp;
        apacheStateDropdown = priceSectionPage.apacheStateDropdown;
        apacheVariantDropdown = priceSectionPage.apacheVariantDropdown;
        ApacheVariants = priceSectionPage.ApacheVariants;
        apacheStates = priceSectionPage.apacheStates;

    }
    Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/ORP_08042025 (2).xlsx", "Sheet1");

    @Given("navigate to {string} page in {string}")
    public void navigateToThePageIn(String vehicle, String environment) throws IOException {
        this.env = environment;
        SelectedVehicle=vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("click on the dropdown to select {string} and state")
    public void clickOnTheDropdownsToSelectAndState(String variant) throws InterruptedException {
        selectedVariant = variant;
        System.out.println("Selected Variant: " + selectedVariant);
        apacheVariantDropdown.click();
        List<WebElement> variants = driver.findElements(ApacheVariants);
        System.out.println("vars"+variant);
        for (WebElement variantElement : variants)
        {
            if (variantElement.getText().equalsIgnoreCase(selectedVariant))
            {
                Thread.sleep(4000);
                variantElement.click();
                System.out.println("var-"+variantElement.getText());
                break;
            }
        }
        Thread.sleep(4000);

        priceSectionPage.ClickApacheStateDropdown();
    }

    @Then("fetch the list of states")
    public void getTheListOfStates()
    {
        stateList = driver.findElements(apacheStates);
    }


    @Then("for each state get the UI Ex-showroom prices")
    public void forEachStateGetTheUIOnRoadPrices() throws InterruptedException, IOException {
        for (int i = 0; i < stateList.size(); i++) {
            try {
                stateList = driver.findElements(apacheStates);
                WebElement stateElement = stateList.get(i);
                state = stateElement.getText();
                System.out.println("\nState: " + state);

                stateElement.click();
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                //priceSectionPage.ClickOnRoadPrice();
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

                if (i < stateList.size() - 1) {
                    priceSectionPage.ClickApacheStateDropdown();
                }

                Thread.sleep(2000);
                List<WebElement> onRoadModels = driver.findElements(By.xpath("//div[@id='priceLable']//dl[@class='row']/dt[@class='col-xs-6']"));
                List<WebElement> onRoadPrices = driver.findElements(By.xpath("//div[@id='priceLable']//dl[@class='row']/dd[@class='col-xs-6']/span[@class='productAmount']"));


                uiPrices = new HashMap<>();
                if (onRoadModels.isEmpty() || onRoadPrices.isEmpty()) {
                    System.out.println("No prices available in UI for state: " + state);
                } else {
                    for (int j = 0; j < onRoadModels.size(); j++) {
                        String model = onRoadModels.get(j).getText();
                        String priceText = onRoadPrices.get(j).getText().trim();
                        String finalPrice = priceText.replace(" ", "");
                        uiPrices.put(model, finalPrice);
                    }
                }


                //System.out.println("uiPrices-" + uiPrices);
                getAPIOnRoadPrices();
                compareUIAndAPIOnRoadPrices();

            } catch (Exception e) {
                System.out.println("Error processing state: " + state + " - " + e.getMessage());
                throw e;
            }
        }
    }

    @Then("for each state get the API Ex-showroom prices")
    public void getAPIOnRoadPrices()
    {
        JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
        Response response;

        if (env.equalsIgnoreCase("UAT")) {
            //System.out.println("SelectedVar-" + selectedVariant);
            response = GetExshowroomPriceUAT(selectedVariant, stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            //System.out.println("SelectedVar-" + selectedVariant);
            response = GetExshowroomPriceProd(selectedVariant, stateCode);
        } else {
            System.out.println("Environment not recognized: " + env);
            return;
        }

        if (response.getStatusCode() == 204) {
            System.out.println("No content in API response for state: " + state);
            assertEquals("ORP not available: " + state, driver.findElement(By.cssSelector("#road-price p")).getText() + ": " + state);
            return;
        }

        String responseBody = response.getBody().asString();
        if (responseBody == null || responseBody.isEmpty() || response.getStatusCode() == 204) {
            System.out.println("API response is empty for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        try {
            apiPrices = response.jsonPath().getList("");
        } catch (Exception e) {
            System.out.println("Failed to parse API response for state: " + state);
        }

    }

    @Then("compare the UI and API Ex-showroom prices")
    public void compareUIAndAPIOnRoadPrices() throws IOException
    {

        if (apiPrices.isEmpty() && uiPrices.isEmpty()) {
            System.out.println("Both UI and API have no prices for state: " + state);
            return;
        }

        for (Map<String, Object> apiPrice : apiPrices)
        {
            String variantName = (String) apiPrice.get("VariantNameExtension");
            String AlternativeVariantName = (String) apiPrice.get("AlternateModelName");
            String apiOnRoadPrice = (String) apiPrice.get("Price");

            //System.out.println("apiPrc= "+apiPrices);
            if (uiPrices.containsKey(variantName))
            {
                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
                System.out.println("Comparing prices for model: " + variantName);
                //System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice);
                //assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                //System.out.println(uiPrices.containsKey(variantName));
                // Get Excel price
                String key = selectedVariant.toUpperCase()+ "|" + variantName + "|" + MappedStateName(state);
                //System.out.println("KeyValue: " + key);
                if (key.contains("Chandigarh") || key.contains("Himachal Pradesh") != key.contains("TVS Radeon"))
                {
                    System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice);
                    assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiOnRoadPrice), uiOnRoadPrice);
                }
                else {
                    long roundedExcelPrice = 0;
                    if (excelPrices.containsKey(key)) {
                        String excelExshowroomPrice = excelPrices.get(key).get("Ex-ShowRoomPrice");
                        System.out.println("Excel Price: " + excelExshowroomPrice);

                        roundedExcelPrice = Math.round(Float.parseFloat(excelExshowroomPrice));
                    } else {
                        System.out.println("Model " + variantName + " not found in Excel data");
                    }

                    // Print all prices together
                    System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                    // Assertions
                    assertEquals("Price mismatch for model: " + variantName, uiOnRoadPrice, Integer.parseInt(apiOnRoadPrice));
                    assertEquals("Price mismatch for model: " + variantName + " with Excel", uiOnRoadPrice, roundedExcelPrice, 0.0);
                }
            }
            else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
//            if (uiPrices.containsKey(AlternativeVariantName))
//            {
//                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(AlternativeVariantName));
//                System.out.println("Comparing prices for model: " + AlternativeVariantName);
//                String key = selectedVariant+ "|" + variantName + "|" + MappedStateName(state);
//                long roundedExcelPrice = 0;
//                if (excelPrices.containsKey(key)) {
//                    double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
//                    roundedExcelPrice = Math.round(excelOnRoadPrice);
//                } else {
//                    System.out.println("Model " + AlternativeVariantName + " not found in Excel data");
//                }
//
//                System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);
//
//                // Assertions
//                assertEquals("Price mismatch for model: " + AlternativeVariantName, apiOnRoadPrice, uiOnRoadPrice);
//                //assertEquals("Price mismatch for model: " + AlternativeVariantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
//            }
//            else {
//                System.out.println("---Alt model----");
//                System.out.println("Model " + AlternativeVariantName + " not found in UI data");
//            }
        }
    }



}

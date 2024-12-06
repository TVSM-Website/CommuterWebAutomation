package StepDefs;

import Utils.ORPExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.PriceSectionPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static APIs.ORPProdAPI.OrpDetailsPROD;
import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ORPExcelUtils.MappedStateName;
import static Utils.ExplicitWait.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PriceValidationStepDef extends Utilities {
    private static final Logger log = LogManager.getLogger(TestRideStepDef.class);

    private Map<String, Map<String, String>> readExcelPrices(String filePath, String sheetName) throws IOException {
        return ORPExcelUtils.readExcelData(filePath, sheetName);
    }

    private final WebDriver driver;
    PriceSectionPage priceSectionPage;
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
    String variantName;
    int uiOnRoadPrice;

    public PriceValidationStepDef() {
        this.driver = WebDriverManager.getDriver();
        priceSectionPage = new PriceSectionPage(driver);
        AcceptCookie = priceSectionPage.AcceptCookie;
        stateDropdown = priceSectionPage.stateDropdown;
        states = priceSectionPage.states;
        selectlanguagePopUp = priceSectionPage.selectlanguagePopUp;
        roninStateDropdown = priceSectionPage.roninStateDropdown;
        raiderPriceSection = priceSectionPage.raiderPriceSection;
        stateDropdownRaider = priceSectionPage.stateDropdownRaider;
        statesRaider = priceSectionPage.statesRaider;

        //selectedVariant = priceSectionPage.selectedVariant;
    }

    @Given("navigate to the tvs brand {string} page in {string}")
    public void navigateToTheTvsBrandPageIn(String vehicle, String environment) throws IOException {
        env = environment;
        selectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("user navigated to the price section and accept the cookies pop up")
    public void user_navigated_to_the_price_section()
    {
        // Check if the language selection pop-up is displayed
        try {
            if (selectlanguagePopUp.isDisplayed())
            {
                By languageSelector = priceSectionPage.getLanguageSelector("English");
                WebElement languageElement = driver.findElement(languageSelector);
                waitForElementToBeClickable(driver, languageElement, 15);
                languageElement.click();
            } else
            {
                System.out.println("Language selection pop-up is not displayed.");
            }
        } catch (NoSuchElementException e)
        {
            System.out.println("Language selection pop-up not found on the page.");
        }
        try {
            if (priceSectionPage.isDisplayed()) {
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                priceSectionPage.clickDismiss();
            }
        } catch (NoSuchElementException e) {
            System.out.println("BookNow pop-up not displayed on the page");
        }

        // Check if the cookies acceptance pop-up is displayed
        try {
            if (AcceptCookie.isDisplayed()) {
                priceSectionPage.ClickAcceptCookies();
            } else {
                System.out.println("Cookies acceptance pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookies acceptance pop-up not found on the page.");
        }
        waitForElementToBeClickable(driver, stateDropdown, 15);
    }

    @When("click on the state dropdown and select the state")
    public void click_on_the_state_dropdown_and_select_the_state() throws InterruptedException {
        //Thread.sleep(1500);
        priceSectionPage.ClickStateDropdown();
        visibilityOfElementLocated(driver, states, 15);
    }

    @When("get the list of all states")
    public void getTheListOfAllStates() {
        stateList = driver.findElements(priceSectionPage.states);
        System.out.println("List of all states fetched successfully.");
    }

    @Then("iterate through each state to select and fetch On-Road prices")
    public void iterateThroughEachStateToFetchPrices() throws InterruptedException, IOException {
        //stateList = driver.findElements(priceSectionPage.states);

        for (int i = 0; i < stateList.size(); i++) {
            stateList = driver.findElements(priceSectionPage.states);
            state = stateList.get(i).getText();
            System.out.println("\nstate: " + state);

            if (!state.isEmpty()) {
                stateList.get(i).click();
            } else {
                break;
            }

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
            priceSectionPage.ClickOnRoadPrice();
            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            if (i < stateList.size() - 1) {
                priceSectionPage.ClickStateDropdown();
            }

            fetchOnRoadPricesForSelectedState();
            compareUIAndAPIPricesForAllStates();
        }
    }

    @Then("fetch On-Road prices for the selected state")
    public void fetchOnRoadPricesForSelectedState() {
        uiPrices.clear();
        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dt[@class='col-xs-6']"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dd/span"));

        if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else {
            for (int j = 0; j < OnRoadModels.size(); j++) {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().replace("*", "").replace("₹", "").replace(",", "").trim();
                uiPrices.put(model, priceText.replace(" ", ""));
            }
        }
        //System.out.println("UI Prices for " + state + " -> " + uiPrices);
    }

    @Then("compare UI and API and Excel prices for all variants in each state")
    public void compareUIAndAPIPricesForAllStates() throws IOException {
        JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT")) {
            response = OrpDetailsUAT(selectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = OrpDetailsPROD(selectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        apiPrices = response.jsonPath().getList("");
        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/orpnewprices.xlsx", "Sheet1");

        for (Map<String, Object> apiPrice : apiPrices) {
            String variantName = (String) apiPrice.get("VariantName");
            int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

            if (uiPrices.containsKey(variantName)) {
                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = selectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
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

//    @Then("validate the prices against Excel data")
//    public void compareExcelPrices() throws IOException {
//        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/NtorqOrp.xlsx", "Sheet1");
//        String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
//
//        if (excelPrices.containsKey(key)) {
//            double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
//            long roundedExcelPrice = Math.round(excelOnRoadPrice);
//            System.out.println("UI Price: " + uiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);
//            assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice,0.0);
//        } else {
//            System.out.println("Model " + variantName + " not found in Excel data");
//        }
//    }

//    @Then("get the On-Road prices for all the states and variants")
//    public void getThePricesForExShowroomForTheVariantsFrom() throws InterruptedException, IOException
//    {
//        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/NtorqOrp.xlsx", "Sheet1");
//
//        List<WebElement> stateList = driver.findElements(states);//driver.findElements(By.xpath("//div[@id='bs-select-1']//ul/li/a/span"));
//        for (int i = 0; i < stateList.size(); i++) {
//            stateList = driver.findElements(priceSectionPage.states);
//            System.out.println("\n state: " + stateList.get(i).getText());
//            state = stateList.get(i).getText();
//
//            //System.out.println("state -" + state);
//
//            WebElement stateElement = stateList.get(i);
//            if (!state.isEmpty()) {
//                stateElement.click();
//            } else {
//                break;
//            }
//
//            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
//            priceSectionPage.ClickOnRoadPrice();
//            //priceSectionPage.ClickOnExshowRoomPrice();
//
//            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
//
//            if (i < stateList.size() - 1) {
//                priceSectionPage.ClickStateDropdown();
//            }
//
//
//            Thread.sleep(2000);
//            List<WebElement> OnRoadModels = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dt[@class='col-xs-6']"));
//
//            //List<WebElement>OnRoadModels=driver.findElements(By.xpath("//div[@id='ex-showroom']/div/dl[@class='row']/dt"));
//            //div[@id='on-road']/dl[@class='row']/dd[@class='col-xs-6 price']
//            List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//div[@id='on-road']/dl[@class='row']/dd/span"));
//
//            //List<WebElement>OnRoadPrices=driver.findElements(By.xpath("//div[@id='ex-showroom']/div/dl/dd/span"));
//
//            uiPrices.clear();
//            if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
//                System.out.println("No prices available in UI for state: " + state);
//            } else {
//                for (int j = 0; j < OnRoadModels.size(); j++) {
//                    String model = OnRoadModels.get(j).getText();
//                    String priceText = OnRoadPrices.get(j).getText().replace("*", "").replace("₹", "").replace(",", "").trim();
//                    //int price = Integer.parseInt(priceText);
//                    String finalPrice = priceText.replace(" ", "");
//                    uiPrices.put(model, finalPrice);
//                }
//            }
//            //System.out.println("uiPrices -" + uiPrices);
//
//            JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
//            String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
//            if (env.equalsIgnoreCase("UAT")) {
//                response = OrpDetailsUAT(SelectedVehicle.replace("_", " "), stateCode);
//            } else if (env.equalsIgnoreCase("PROD")) {
//                response = OrpDetailsPROD(SelectedVehicle.replace("_", " "), stateCode);
//            }
//            if (response.getStatusCode() == 204) {
//                System.out.println("No content in API response for state: " + state);
//                assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
//                continue; // Skip to the next state if API call fails
//            }
//
//            String responseBody = response.getBody().asString();
//            if (responseBody == null || responseBody.isEmpty() || response.getStatusCode() == 204) {
//                System.out.println("API response is empty for state: " + state);
//                assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
//                continue; // Skip to the next state if API response is empty
//            }
//            try {
//                apiPrices = response.jsonPath().getList("");
//            } catch (Exception e) {
//                System.out.println("Failed to parse API response for state: " + state);
//                continue; // Skip to the next state if parsing fails
//            }
//
//            if (uiPrices.isEmpty() && apiPrices.isEmpty()) {
//                System.out.println("Both UI and API have no prices for state: " + state);
//                continue; // Both UI and API have no prices, skip to the next state
//            }
//
//            if (apiPrices.isEmpty()) {
//                System.out.println("No prices available in API for state: " + state);
//                assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
//                continue; // Skip to the next state if no prices are found in API
//            }
////            apiPrices = apiPrices.stream()
////                    .filter(apiPrice -> selectedVariant.equals(apiPrice.get("VariantName")))
////                    .collect(Collectors.toList());
//            for (Map<String, Object> apiPrice : apiPrices) {
//                String variantName = (String) apiPrice.get("VariantName");
//                int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");
//
//                if (uiPrices.containsKey(variantName)) {
//                    int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
//                    System.out.println("Comparing prices for model: " + variantName);
//                    System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice);
//                    assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
//
//                    // Compare with Excel prices
//                    String mappedState = MappedStateName(state);
//                    String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + mappedState;
//                    //System.out.println("Generated Key: " + key);
//                    //System.out.println("Available Keys in Excel Data: " + excelPrices.keySet());
//                    if (excelPrices.containsKey(key)) {
//                        double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
//                        long roundedExcelPrice = Math.round(excelOnRoadPrice);
//
//                        System.out.println("Excel Price: " + roundedExcelPrice);
//                        assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
//                        assertEquals("Price mismatch for model: " + variantName + " between API and Excel", roundedExcelPrice, apiOnRoadPrice, 0.0);
//                    } else {
//                        System.out.println("Model " + variantName + " not found in Excel data");
//                    }
//                } else {
//                    System.out.println("Model " + variantName + " not found in UI data");
//                }
//            }
//        }
//    }

    @When("the user navigates to the price section and accepts the cookies pop-up")
    public void userNavigatedToRoninPriceSectionAndAcceptTheCookiesPopUp() {
        // Check if the language selection pop-up is displayed
        try {
            if (selectlanguagePopUp.isDisplayed()) {
                By languageSelector = priceSectionPage.getLanguageSelector("English");
                WebElement languageElement = driver.findElement(languageSelector);
                waitForElementToBeClickable(driver, languageElement, 15);
                languageElement.click();
            } else {
                System.out.println("Language selection pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Language selection pop-up not found on the page.");
        }
        try {
            if (priceSectionPage.isDisplayed()) {
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                priceSectionPage.clickDismiss();
            }
        } catch (NoSuchElementException e) {
            System.out.println("BookNow pop-up not displayed on the page");
        }

        // Check if the cookies acceptance pop-up is displayed
        try {
            if (AcceptCookie.isDisplayed()) {
                priceSectionPage.ClickAcceptCookies();
            } else {
                System.out.println("Cookies acceptance pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookies acceptance pop-up not found on the page.");
        }
        waitForElementToBeClickable(driver, roninStateDropdown, 15);

    }

    @When("click the state dropdown and select the state")
    public void clickTheStateDropdownAndSelectTheState() {
        priceSectionPage.ClickStateDropdown();
        visibilityOfElementLocated(driver, states, 15);

    }

    @Then("get the On-Road prices for all the states and variants for {string}")
    public void getTheOnRoadPricesForVariant(String variant) throws InterruptedException, IOException
    {
        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/orpnewprices.xlsx", "Sheet1");

        boolean variantFound = false;
        for (int c = 0; c < 6; c++) { // Assuming a maximum of 6 variants in the slider
            String currentVariant = driver.findElement(By.cssSelector("div[class='item active'] h4")).getText();

            if (currentVariant.equalsIgnoreCase(variant)) {
                variantFound = true;

                // Wait for the state dropdown to be visible
                visibilityOfElementLocated(driver, states, 15);
                priceSectionPage.clickRaiderStateDropdown();

                List<WebElement> stateList = driver.findElements(statesRaider);
                for (int i = 0; i < stateList.size(); i++) {
                    stateList = driver.findElements(priceSectionPage.statesRaider);
                    System.out.println("\nState: " + stateList.get(i).getText());
                    state = stateList.get(i).getText();

                    WebElement stateElement = stateList.get(i);
                    if (!state.isEmpty()) {
                        stateElement.click();
                    } else {
                        break;
                    }

                    waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                    priceSectionPage.ClickOnRoadPriceRaider();

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

                    JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
                    String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
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
                        String variantName = ((String) apiPrice.get("VariantName")).toUpperCase();

                        // Fetch the price from API
                        int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

                        if (uiPrices.containsKey(variantName)) {
                            int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
                            System.out.println("Comparing prices for model: " + variantName);

                            // Normalize Excel key lookup
                            String excelKey = selectedVehicle.replace("_", " ") + "|" + apiPrice.get("VariantName") + "|" + MappedStateName(state); // Use original casing for VariantName in Excel key
                            //System.out.println("Excel Key: " + excelKey);

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
                        } else {
                            System.out.println("Model " + variantName + " not found in UI data. Available models: " + uiPrices.keySet());
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

    @When("the user navigates to the price section of raider page and accepts the cookies pop-up")
    public void theUserNavigatesToThePriceSectionOfRaiderPageAndAcceptsTheCookiesPopUp() {
        // Check if the language selection pop-up is displayed
        try {
            if (selectlanguagePopUp.isDisplayed()) {
                By languageSelector = priceSectionPage.getLanguageSelector("English");
                WebElement languageElement = driver.findElement(languageSelector);
                waitForElementToBeClickable(driver, languageElement, 15);
                languageElement.click();
            } else {
                System.out.println("Language selection pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Language selection pop-up not found on the page.");
        }
        try {
            if (priceSectionPage.isDisplayed()) {
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                priceSectionPage.clickDismiss();
            }
        } catch (NoSuchElementException e) {
            System.out.println("BookNow pop-up not displayed on the page");
        }

        // Check if the cookies acceptance pop-up is displayed
        try {
            if (AcceptCookie.isDisplayed()) {
                priceSectionPage.ClickAcceptCookies();
            } else {
                System.out.println("Cookies acceptance pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookies acceptance pop-up not found on the page.");
        }
        Utilities.scrollToElement(raiderPriceSection);
        waitForElementToBeClickable(driver, stateDropdownRaider, 15);
    }

    @Then("get the On-Road prices for all the states and variants for raider")
    public void getTheOnRoadPricesForAllTheStatesAndVariantsForRaider() throws InterruptedException, IOException
    {
        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/orpnewprices.xlsx", "Sheet1");

        for (int c = 0; c < 6; c++) {
            // Click the right arrow to navigate to the next variant
            if (c > 0) {
                priceSectionPage.clickRightArrow();
                Thread.sleep(2000);
            }

            //visibilityOfElementLocated(driver, selectedVariant, 10);
            selectedVariant = driver.findElement(By.cssSelector("div[class='item active'] h4")).getText();

            System.out.println("Selected Variant: " + selectedVariant);

            // wait for the state dropdown to be visible and click it
            visibilityOfElementLocated(driver, states, 15);
            priceSectionPage.clickRaiderStateDropdown();

            List<WebElement> stateList = driver.findElements(statesRaider);
            for (int i = 0; i < stateList.size(); i++) {
                stateList = driver.findElements(priceSectionPage.statesRaider);
                System.out.println("\n states: " + stateList.get(i).getText());
                state = stateList.get(i).getText();

                WebElement stateElement = stateList.get(i);
                if (!state.isEmpty()) {
                    stateElement.click();
                } else {
                    break;
                }

                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                priceSectionPage.ClickOnRoadPriceRaider();

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

                    //System.out.println("model -" + model);
                    //System.out.println("price -" + finalPrice);
                    uiPrices.put(model, finalPrice);
                }
                //System.out.println("uiPrices -" + uiPrices);
                JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
                String stateCode = json.getString("stateCodes." + state.replace(" ", ""));
                Response response = OrpDetailsUAT(selectedVehicle.replace("_", " "), stateCode);

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
                            .filter(apiPrice -> selectedVariant.equals(apiPrice.get("VariantName")))
                            .collect(Collectors.toList());

                    //System.out.println("apiPrices -" + apiPrices);
                    for (Map<String, Object> apiPrice : apiPrices) {
                        String variantName = (String) apiPrice.get("VariantName");
                        //System.out.println("apiVariantName -" + variantName);
                        int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

                        //System.out.println("variantName -" + variantName);
                        // Handle cases where the UI does not have the variant present in the API
                        if (uiPrices.containsKey(variantName.toUpperCase())) {
                            System.out.println(uiPrices.containsKey(variantName));
                            int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName.toUpperCase()));
                            System.out.println("Comparing prices for model: " + variantName);
                            // Get Excel price
                            String key = selectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
                            long roundedExcelPrice = 0;
                            if (excelPrices.containsKey(key)) {
                                double excelOnRoadPrice = Float.parseFloat(excelPrices.get(key).get("OnRoadPrice"));
                                roundedExcelPrice = Math.round(excelOnRoadPrice);
                            } else {
                                System.out.println("Model " + variantName + " not found in Excel data");
                            }

                            // Print all prices together
                            System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + apiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);
                            //System.out.println("UI Price: " + uiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                            // Assertions
                            assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                            assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);
                        } else {
                            System.out.println("Model " + variantName + " not found in UI data");
                        }
                    }
                }
                else {
                    apiPrices = apiPrices.stream()
                            .filter(apiPrice -> selectedVariant.equals(apiPrice.get("VariantName")))
                            .collect(Collectors.toList());
                    for (Map<String, Object> apiPrice : apiPrices) {
                        String variantName = (String) apiPrice.get("VariantName");
                        //System.out.println("apiVariantName -" + variantName);
                        int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

                        //System.out.println("variantName -" + variantName);
                        // Handle cases where the UI does not have the variant present in the API
                        if (uiPrices.containsKey(variantName)) {
                            System.out.println(uiPrices.containsKey(variantName));
                            int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName));
                            System.out.println("Comparing prices for model: " + variantName);
                            // Get Excel price
                            String key = selectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);
                            long roundedExcelPrice = 0;
                            if (excelPrices.containsKey(key)) {
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
                        } else {
                            System.out.println("Model " + variantName + " not found in UI data");
                        }
                    }
                }
            }
        }
    }


}
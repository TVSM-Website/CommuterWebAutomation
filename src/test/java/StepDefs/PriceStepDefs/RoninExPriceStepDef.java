package StepDefs.PriceStepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static APIs.ExShowroomProdAPI.GetExshowroomPriceProd;
import static APIs.ExShowroomUATAPI.GetExshowroomPriceUAT;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.Utilities.mapVariantFromBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoninExPriceStepDef {
    private final WebDriver driver;
    String env;
    String selectedVehicle;
    String selectedVehicleUrl;
    String state;
    By states=By.xpath("//div[@id='pricestate-drop']/ul/li");
    Response response;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;

    private Map<String, Map<String, String>> readExcelPrices() throws IOException {
        return ExShowRoomExcelUtils.readExcelDataRonin("src/test/Resources/TestData/ORP_UAT_05052025.xlsx", "ORP");
    }
    public RoninExPriceStepDef()
    {
        this.driver = WebDriverManager.getDriver();

    }


    @Given("navigate to the {string} page on {string}")
    public void navigateToThePageOn(String vehicle, String environment) throws IOException {
        env = environment;
        selectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='30%';");
    }

    @When("the user selects each state from the dropdown and fetches Ex-Showroom prices")
    public void theUserSelectsEachStateFromTheDropdownAndFetchesPrices() throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for dropdown
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pricestate']")));
        if (dropdown == null) {
            throw new RuntimeException("State dropdown is not found on the page!");
        }

        // Get all states initially
        List<WebElement> allStates = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(states));
        System.out.println("Total states found: " + allStates.size());

        for (int i = 0; i < allStates.size(); i++) {
            // Reopen dropdown each time
            dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pricestate']")));
            dropdown.click();

            // Re-fetch updated list
            allStates = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(states));
            WebElement currentState = allStates.get(i);
            state = currentState.getText();
            System.out.println("\nSelected state: " + state);

            if (state.isEmpty()) {
                System.out.println("Empty state name found. Skipping...");
                continue;
            }

            // Scroll to the state element
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", currentState);
            Thread.sleep(500); //wait to ensure visibility
            currentState.click();

            // Wait for loader to disappear
            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            // Fetch and compare prices
            fetchExShowroomPricesForTheSelectedState();
            compareUIAndAPIExShowroomPricesForAllVariantsInEachState();
        }
    }

    @Then("fetch Ex-Showroom prices for the selected state")
    public void fetchExShowroomPricesForTheSelectedState() {
        System.out.println("state: " + state);

        uiPrices.clear();

        List<WebElement> ExPriceModels = driver.findElements(By.xpath("//ul[contains(@class, 'accrodion-tab')]/li[contains(@class, 'variant-name')]"));
        List<WebElement> ExShowroomPrices = driver.findElements(By.xpath("//ul[contains(@class, 'accrodion-tab')]//li[contains(@class, 'variant-price')]//span[@class='price']"));

        if (ExPriceModels.isEmpty() || ExShowroomPrices.isEmpty())
        {
            System.out.println("No prices available in UI for state: " + state);
        } else
        {
            for (int j = 0; j < ExPriceModels.size(); j++)
            {
                String model = ExPriceModels.get(j).getText();
                String priceText = ExShowroomPrices.get(j).getText().trim().replaceAll("\\s+", "");
                uiPrices.put(model, priceText.replace(" ", ""));
                //System.out.println("Model: " + model + " Price: " + priceText);
            }
        }
    }

    @Then("compare UI and API Ex-Showroom prices for all variants in each state")
    public void compareUIAndAPIExShowroomPricesForAllVariantsInEachState() throws IOException, InterruptedException {
        JsonPath json = new JsonPath(new File("src/test/Resources/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT")) {
            response = GetExshowroomPriceUAT(selectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = GetExshowroomPriceProd(selectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        //apiPrices = response.jsonPath().getList("");

        try {
            apiPrices = response.jsonPath().getList("");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON for state: " + state);
            System.out.println("Response:\n" + response.getBody().asString());
            throw e; // or fail the test
        }
        Map<String, Map<String, String>> excelPrices = readExcelPrices();

        for (Map<String, Object> apiPrice : apiPrices)
        {
            String variantName = (String) apiPrice.get("VariantNameExtension");
            String apiexShowroomPrice = (String) apiPrice.get("Price");

            if (uiPrices.containsKey(variantName))
            {
                System.out.println("Variant Name: " + variantName);
                int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));

                String[] parsed = Utilities.VariantMapper.parseVariantAndColor(variantName);
                if (parsed[0] == null || parsed[1] == null) {
                    System.out.println("Unable to parse variant/color for: " + variantName);
                    continue;
                }

                String mappedVariant = mapVariantFromBase(parsed[0].trim());
                String mappedColor = parsed[1].trim().toUpperCase();
                String model = selectedVehicle.replace("_", " ").trim();
                String excelKey = model + "|" + mappedVariant + "|" + mappedColor + "|" + ExShowRoomExcelUtils.MappedStateName(state);

                if (excelKey.contains("Chandigarh") || excelKey.contains("Himachal Pradesh"))
                {
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice);
                    assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);
                }
                else
                {
                    long roundedExcelPrice = 0;
                    if (excelPrices.containsKey(excelKey)) {
                        String excelExshowRoomPrice = excelPrices.get(excelKey).get("Ex-ShowRoomPrice");
                        roundedExcelPrice = Math.round(Float.parseFloat(excelExshowRoomPrice));
                    } else {
                        System.out.println(" Model " + variantName + " not found in Excel data with key: " + excelKey);
                    }

                    // Compare prices
                    System.out.println(" Comparing prices for model: " + variantName);
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice + ", Excel Price: " + roundedExcelPrice);

                    assertEquals("Price mismatch for model: " + variantName, Integer.parseInt(apiexShowroomPrice), UIexShowRoomPrice);
                    assertEquals("Price mismatch with Excel for model: " + variantName, roundedExcelPrice, UIexShowRoomPrice);

                }
            }
            else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
        }
    }
}

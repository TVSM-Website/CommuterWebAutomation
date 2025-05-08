package StepDefs.PriceStepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.ORPExcelUtils;
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

import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ExplicitWait.waitForElementToBeClickable;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.Utilities.mapVariantFromBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoninOrpStepDef {
    private final WebDriver driver;
    String env;
    String selectedVehicle;
    String selectedVehicleUrl;
    String state;
    By states=By.xpath("//div[@id='pricestate-drop']/ul/li");
    Response response;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;


    public RoninOrpStepDef() {
        this.driver = WebDriverManager.getDriver();

    }

    private Map<String, Map<String, String>> readExcelPrices() throws IOException {
        return ORPExcelUtils.readExcelDataRonin("src/test/Resources/TestData/ORP_UAT_05052025.xlsx", "ORP");
    }

    @Given("navigate to the {string} page on {string} environment")
    public void navigateToThePageOnEnvironment(String vehicle, String environment) throws IOException {
        env = environment;
        selectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='30%';");

    }

    @When("user selects each state from the dropdown and fetches On-Road prices")
    public void userSelectsEachStateFromTheDropdownAndFetchesOnRoadPrices() throws InterruptedException, IOException
    {
        waitForElementToBeClickable(driver, driver.findElement(By.id("brand-page-orp")), 10);
        driver.findElement(By.id("brand-page-orp")).click();
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
            System.out.println("\nSelecting state: " + state);

            if (state.isEmpty()) {
                System.out.println("Empty state name found. Skipping...");
                continue;
            }

            // Scroll to the state element
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", currentState);
            Thread.sleep(500); // brief wait to ensure visibility
            currentState.click();

            // Wait for loader to disappear
            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            // Fetch and compare prices
            fetchOnRoadPricesForStates();
            compareUIAndAPIOnRoadPrices();
        }

    }

    @Then("fetch On-Road prices for the selected state and variant")
    public void fetchOnRoadPricesForStates() {
        System.out.println("state: " + state);

        uiPrices.clear();

        List<WebElement> ORPModels = driver.findElements(By.xpath(".//li[contains(@class, 'variant-name')]/span"));
        List<WebElement> ORPPrices = driver.findElements(By.xpath(".//li[contains(@class, 'variant-price')]//span[@class='price productAmount']"));

        if (ORPModels.isEmpty() || ORPPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else
        {
            for (int j = 0; j < ORPModels.size(); j++) {
                String model = ORPModels.get(j).getText();
                String priceText = ORPPrices.get(j).getText().trim().replaceAll("\\s+", "");
                uiPrices.put(model, priceText.replace(" ", ""));
                //System.out.println("Model: " + model + " Price: " + priceText);
            }
        }
    }

    @Then("compare UI and API On-Road prices for all variants in each state")
    public void compareUIAndAPIOnRoadPrices() throws IOException {
        JsonPath json = new JsonPath(new File("src/test/Resources/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT")) {
            response = OrpDetailsUAT(selectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = OrpDetailsUAT(selectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        try {
            apiPrices = response.jsonPath().getList("");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON for state: " + state);
            System.out.println("Response:\n" + response.getBody().asString());
            throw e;
        }
        Map<String, Map<String, String>> excelPrices = readExcelPrices();

        for (Map<String, Object> apiPrice : apiPrices) {
            String variantName = (String) apiPrice.get("AlternateModelName");
            int apiOnRoadPrice = (int) apiPrice.get("OnRoadPrice");

            if (uiPrices.containsKey(variantName))
            {
                System.out.println("Variant Name: " + variantName);
                int uiOnRoadPrice = Integer.parseInt(uiPrices.get(variantName).replace("*", ""));

                String[] parsed = Utilities.VariantMapper.parseVariantAndColor(variantName);
                if (parsed[0] == null || parsed[1] == null) {
                    System.out.println("Unable to parse variant/color for: " + variantName);
                    continue;
                }

                String mappedVariant = mapVariantFromBase(parsed[0].trim());
                String mappedColor = parsed[1].trim().toUpperCase();
                String model = selectedVehicle.replace("_", " ").trim();
                String excelKey = model + "|" + mappedVariant + "|" + mappedColor + "|" + ExShowRoomExcelUtils.MappedStateName(state);

                long roundedExcelPrice = 0;
                if (excelPrices.containsKey(excelKey)) {
                    String excelOnRoadPPrice = excelPrices.get(excelKey).get("OnRoadPrice");
                    roundedExcelPrice = Math.round(Float.parseFloat(excelOnRoadPPrice));
                } else {
                    System.out.println(" Model " + variantName + " not found in Excel data with key: " + excelKey);
                }

                // Compare prices
                System.out.println(" Comparing prices for model: " + variantName);
                System.out.println("UI Price: " + uiOnRoadPrice + ", API Price: " + uiOnRoadPrice + ", Excel Price: " + roundedExcelPrice);

                assertEquals("Price mismatch for model: " + variantName, apiOnRoadPrice, uiOnRoadPrice);
                assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, uiOnRoadPrice, 0.0);

            } else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
        }
    }
}


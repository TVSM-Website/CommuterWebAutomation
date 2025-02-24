package StepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import io.cucumber.java.en.And;
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
import static Utils.ExplicitWait.visibilityOfElementLocated;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.ORPExcelUtils.MappedStateName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoninExPriceStepDef
{
    private final WebDriver driver;
    String env;
    String selectedVehicle;
    String selectedVehicleUrl;
    String state;
    By states=By.xpath("//div[@id='pricestate-drop']/ul/li");
    Response response;
    WebElement stateDropdown;
    private List<WebElement> stateList;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;

    private Map<String, Map<String, String>> readExcelPrices(String filePath, String sheetName) throws IOException {
        return ExShowRoomExcelUtils.readExcelData(filePath, sheetName);
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

    }


    @When("the user clicks on state dropdown")
    public void theUserSelectsEachStateFromTheDropdownOneByOne() throws InterruptedException {
        // Scroll to price section first
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for dropdown to be present
        stateDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pricestate']")));

        if (stateDropdown == null) {
            throw new RuntimeException("State dropdown is not found on the page!");
        }

        // Scroll to price section first
        WebElement priceSection = driver.findElement(By.xpath("//div[@class='priceTabel pt-8']")); // Update the actual locator
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", priceSection);
        Thread.sleep(2000); // Small pause to stabilize UI

        // Scroll back up to dropdown before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stateDropdown);
        Thread.sleep(1000); // Small pause

        stateDropdown.click();
        visibilityOfElementLocated(driver, states, 15);
    }

    @Then("fetch the Ex-Showroom price for the selected state")
    public void fetchTheExShowroomPriceForTheSelectedState()
    {
        stateList = driver.findElements(states);
        System.out.println("List of all states fetched successfully."+stateList.size());
    }


    @Then("iterate through each state to select and fetch Ex-Showroom prices")
    public void iterateThroughEachStateToSelectAndFetchExShowroomPrices() throws InterruptedException, IOException {
        stateList = driver.findElements(states);

        for (int i = 0; i < stateList.size(); i++) {
            stateList = driver.findElements(states);
            state = stateList.get(i).getText();
            System.out.println("\nstate: " + state);

            if (!state.isEmpty()) {
                stateList.get(i).click();
            } else {
                break;
            }

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            // **Scroll to price section**
            WebElement priceSection = driver.findElement(By.xpath("//div[@class='priceTabel pt-8']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", priceSection);
            Thread.sleep(3000); // **Extra wait to ensure all prices load**

            fetchExShowroomPricesForTheSelectedState();
            compareUIAndAPIExShowroomPricesForAllVariantsInEachState();
            if (i < stateList.size() - 1) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stateDropdown);
                Thread.sleep(1000);
                stateDropdown.click();
                Thread.sleep(3000);
            }
        }
    }

    @Then("fetch Ex-Showroom prices for the selected state")
    public void fetchExShowroomPricesForTheSelectedState() throws InterruptedException {
        uiPrices.clear();

        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//ul[contains(@class, 'accrodion-tab')]/li[contains(@class, 'variant-name')]"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//ul[contains(@class, 'accrodion-tab')]//li[contains(@class, 'variant-price')]//span[@class='price']"));

        if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else {
            // **Scroll through each model to trigger lazy loading**
            for (int j = 0; j < OnRoadModels.size(); j++) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", OnRoadModels.get(j));
                Thread.sleep(500); // Allow time for each model to load properly
            }

            // **Now fetch prices after scrolling**
            for (int j = 0; j < OnRoadModels.size(); j++) {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().trim().replaceAll("\\s+", "");
                uiPrices.put(model, priceText.replace(" ", ""));
                System.out.println("Model: " + model + " Price: " + priceText);
            }
        }
        }

    @Then("compare UI and API Ex-Showroom prices for all variants in each state")
    public void compareUIAndAPIExShowroomPricesForAllVariantsInEachState() throws IOException {
        JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT"))
        {
            response = GetExshowroomPriceUAT(selectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = GetExshowroomPriceProd(selectedVehicle.replace("_", " "), stateCode);
        }

        if (response.getStatusCode() == 204 || response.getBody().asString().isEmpty()) {
            System.out.println("No content in API response for state: " + state);
            assertTrue("UI prices should also be empty for state: " + state, uiPrices.isEmpty());
            return;
        }

        apiPrices = response.jsonPath().getList("");
        Map<String, Map<String, String>> excelPrices = readExcelPrices("src/test/Resources/TestData/orpnewprices.xlsx", "Sheet1");

        for (Map<String, Object> apiPrice : apiPrices) {
            String variantName = (String) apiPrice.get("VariantNameExtension");
            String apiexShowroomPrice = (String) apiPrice.get("Price");

            if (uiPrices.containsKey(variantName))
            {
                int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = selectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);

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

package StepDefs;

import Utils.ExShowRoomExcelUtils;
import Utils.ORPExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.tvs.pages.TvsSportPricePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import static APIs.ORPProdAPI.OrpDetailsPROD;
import static APIs.ORPUATAPI.OrpDetailsUAT;
import static Utils.ExplicitWait.*;
import static Utils.ExplicitWait.waitForLoaderToDisappear;
import static Utils.ORPExcelUtils.MappedStateName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TvsSportORPStepDef
{
    WebDriver driver;
    String SelectedVehicle;
    String selectedVehicleUrl;
    String env;
    By states;
    String state;
    WebElement AcceptCookie;
    WebElement stateDropdown;
    By selectlanguagePopUp;
    Response response;
    TvsSportPricePage sportExPricePage;
    private List<WebElement> stateList;
    Map<String, String> uiPrices = new HashMap<>();
    List<Map<String, Object>> apiPrices;

    private Map<String, Map<String, String>> readExcelPrices(String filePath, String sheetName) throws IOException {
        return ORPExcelUtils.readExcelData(filePath, sheetName);
    }
    public TvsSportORPStepDef() {
        this.driver = WebDriverManager.getDriver();
        sportExPricePage= new TvsSportPricePage(driver);
        states= sportExPricePage.states;
        selectlanguagePopUp = sportExPricePage.selectlanguagePopUp;
        AcceptCookie = sportExPricePage.AcceptCookie;
        stateDropdown=sportExPricePage.stateDropdown;

    }


    @Given("navigate to {string} brand page on {string}")
    public void navigateToBrandPage(String vehicle, String environment) throws IOException {
        env = environment;
        SelectedVehicle = vehicle;
        selectedVehicleUrl = Utilities.getVehicleUrl(vehicle, env);
        driver.get(selectedVehicleUrl);
    }

    @When("user navigated to price section and accept the cookies")
    public void userNavigatedToPriceSectionAndAcceptTheCookiesPopUp() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

            WebElement selectLanguagePopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.language-popup.show")));
            if (selectLanguagePopUp.isDisplayed())
            {
                By languageSelector = sportExPricePage.getLanguageSelector("English");
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
            if (sportExPricePage.isDisplayed()) {
                waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
                sportExPricePage.clickDismiss();
            }
        } catch (NoSuchElementException e) {
            System.out.println("BookNow pop-up not displayed on the page");
        }

        // Check if the cookies acceptance pop-up is displayed
        try {
            if (AcceptCookie.isDisplayed()) {
                sportExPricePage.ClickAcceptCookies();
            } else {
                System.out.println("Cookies acceptance pop-up is not displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookies acceptance pop-up not found on the page.");
        }
        Thread.sleep(3000);
        waitForElementToBeClickable(driver, stateDropdown, 15);
    }

    @When("click on the state dropdown and fetch all the states")
    public void clickOnTheStateDropdownAndFetchAllStates()
    {
        sportExPricePage.ClickStateDropdown();
        visibilityOfElementLocated(driver, states, 15);
    }

    @Then("get all states for the selected vehicle")
    public void fetchAllStatesForTheSelectedVehicle()
    {
        stateList = driver.findElements(sportExPricePage.states);
        System.out.println("List of all states fetched successfully.");
    }

    @Then("iterate through each state to select and get on-road prices")
    public void iterateThroughEachStateToSelectAndGetExShowRoomPrices() throws IOException, InterruptedException {
        for (int i = 0; i < stateList.size(); i++)
        {
            stateList = driver.findElements(sportExPricePage.states);
            state = stateList.get(i).getText();
            System.out.println("\nstate: " + state);

            if (!state.isEmpty()) {
                stateList.get(i).click();
            } else {
                break;
            }

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);
            sportExPricePage.ClickOnRoadPrice();

            waitForLoaderToDisappear(driver, By.className("loader_ajax"), 15);

            if (i < stateList.size() - 1) {
                sportExPricePage.ClickStateDropdown();
                Thread.sleep(2000);
            }
            Thread.sleep(3000);
            fetchExShowroomPricesForTheSelectedStates();
            //Thread.sleep(2000);
            compareUIAndAPIPricesWithExcelPrice();
        }

    }

    @Then("fetch On-Road prices for the selected states")
    public void fetchExShowroomPricesForTheSelectedStates()
    {
        uiPrices.clear();
        List<WebElement> OnRoadModels = driver.findElements(By.xpath("//div[@class='tvs-table-container']/table/tbody/tr/td[1]"));
        List<WebElement> OnRoadPrices = driver.findElements(By.xpath("//div[@class='tvs-table-container']/table/tbody/tr/td[2]/span"));

        if (OnRoadModels.isEmpty() || OnRoadPrices.isEmpty()) {
            System.out.println("No prices available in UI for state: " + state);
        } else {
            for (int j = 0; j < OnRoadModels.size(); j++)
            {
                String model = OnRoadModels.get(j).getText();
                String priceText = OnRoadPrices.get(j).getText().trim();
                uiPrices.put(model, priceText.replace(" ", ""));
            }
        }
        // System.out.println("uiprice- "+uiPrices);
    }

    @Then("compare UI and API prices with Excel prices for all the variants and states")
    public void compareUIAndAPIPricesWithExcelPrice() throws IOException {
        JsonPath json = new JsonPath(new File("src/test/Resources/TestData/StateCode.json"));
        String stateCode = json.getString("stateCodes." + state.replace(" ", ""));

        if (env.equalsIgnoreCase("UAT"))
        {
            response = OrpDetailsUAT(SelectedVehicle.replace("_", " "), stateCode);
        } else if (env.equalsIgnoreCase("PROD")) {
            response = OrpDetailsPROD(SelectedVehicle.replace("_", " "), stateCode);
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
            int apiexShowroomPrice = (int) apiPrice.get("OnRoadPrice");

            if (uiPrices.containsKey(variantName))
            {
                int UIexShowRoomPrice = Integer.parseInt(uiPrices.get(variantName));

                // Get Excel price
                String key = SelectedVehicle.replace("_", " ") + "|" + variantName + "|" + MappedStateName(state);

                if (key.contains("Chandigarh") || key.contains("Himachal Pradesh") != key.contains("TVS Radeon"))
                {
                    System.out.println("Comparing prices for model: " + variantName);
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice);
                    assertEquals("Price mismatch for model: " + variantName, apiexShowroomPrice, UIexShowRoomPrice);
                }
                else
                {
                    long roundedExcelPrice = 0;
                    if (excelPrices.containsKey(key)) {
                        String excelExshowroomPrice = excelPrices.get(key).get("OnRoadPrice");
                        roundedExcelPrice = Math.round(Float.parseFloat(excelExshowroomPrice));

                    } else {
                        System.out.println("Model " + variantName + " not found in Excel data");
                    }

                    // Print all prices together
                    System.out.println("Comparing prices for model: " + variantName);
                    System.out.println("UI Price: " + UIexShowRoomPrice + ", API Price: " + apiexShowroomPrice + ", Excel Price: " + roundedExcelPrice);

                    // Assertions
                    assertEquals("Price mismatch for model: " + variantName, apiexShowroomPrice, UIexShowRoomPrice);
                    assertEquals("Price mismatch for model: " + variantName + " with Excel", roundedExcelPrice, UIexShowRoomPrice);

                }
            } else {
                System.out.println("Model " + variantName + " not found in UI data");
            }
        }
    }
}
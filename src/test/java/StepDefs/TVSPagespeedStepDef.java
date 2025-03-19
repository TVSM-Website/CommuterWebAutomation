package StepDefs;

import Utils.PageSpeedExcelUtils;
import Utils.WebDriverManager;
import com.tvs.pages.PageSpeedPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;

import java.io.IOException;

public class TVSPagespeedStepDef {
    private final WebDriver driver;
    private final PageSpeedPage pageSpeedPage;
    public String url = "https://pagespeed.web.dev";
    public String excelPath = "src/test/Resources/TestData/PageSpeedOutput.xlsx";
    public static int rowCount = 0;
    public static String tvsUrl;


    public TVSPagespeedStepDef() {
        this.driver = WebDriverManager.getDriver();
        pageSpeedPage = new PageSpeedPage(driver);
    }


    @Given("navigates to the Page Speed url")
    public void userNavigatedToPageSpeed() {
        driver.get(url);
    }

    @When("user navigated to home page and enters the TVS website url in {string} environment")
    public void userNavigatedToHomePageAndEntersVSPageUrl(String tvsUrl) throws IOException, InterruptedException {
        pageSpeedPage.ClickSearchUrl(tvsUrl);
        // printing row heading in Excel
        PageSpeedExcelUtils.excel_initiation(excelPath);
        PageSpeedExcelUtils.excel_createRow(excelPath,0);
        PageSpeedExcelUtils.setCellValue(excelPath, "Tvs Url");
        PageSpeedExcelUtils.setCellValue(excelPath," LCP Value - Mobile");
        PageSpeedExcelUtils.setCellValue(excelPath,"FCP Value - Mobile");
        PageSpeedExcelUtils.setCellValue(excelPath," LCP Value - Desktop");
        PageSpeedExcelUtils.setCellValue(excelPath,"FCP Value - Desktop");
        rowCount = rowCount +1;
        PageSpeedExcelUtils.excel_createRow(excelPath, rowCount);
        PageSpeedExcelUtils.setCellValue(excelPath,tvsUrl);
    }

    @Then("prints the LCP and FCP values from Page Speed with TVS Website for mobiles")
    public void printLCPAndFCPValuesForMobiles() throws IOException {
        String LCPValue = pageSpeedPage.Mobile_LCP_Text.getText();
        System.out.println("Mobile LCP Value .....  " + LCPValue);
        PageSpeedExcelUtils.setCellValue(excelPath,LCPValue);
        String FCPValue = pageSpeedPage.Mobile_FCP_Text.getText();
        System.out.println("Mobile FCP Value .....  " + FCPValue);
        PageSpeedExcelUtils.setCellValue(excelPath,FCPValue);
        //PageSpeedExcelUtils.setCellValue(excelPath,"Mobiles");
    }

    @When("user clicks on Desktop and enters the TVS website url in {string} environment")
    public void UsersClicksDesktop(String url1) throws InterruptedException {
        tvsUrl = url1;
        pageSpeedPage.ClickDesktop();
    }

    @Then("prints the LCP and FCP values from Page Speed with TVS Website for Desktop")
    public void printLCPAndFCPValuesForDesktop() throws IOException {

        if (tvsUrl.contains("book-a-ride")){
            String LCPValue = pageSpeedPage.BookRide_Desktop_LCP_Text.getText();
            System.out.println("LCP Value .....  " + LCPValue);
            PageSpeedExcelUtils.setCellValue(excelPath, LCPValue);
            String FCPValue = pageSpeedPage.BookRide_Desktop_FCP_Text.getText();
            System.out.println("FCP Value .....  " + FCPValue);
            PageSpeedExcelUtils.setCellValue(excelPath, FCPValue);
        }
        else {
            String LCPValue = pageSpeedPage.Desktop_LCP_Text.getText();
            System.out.println("LCP Value .....  " + LCPValue);
            PageSpeedExcelUtils.setCellValue(excelPath, LCPValue);
            String FCPValue = pageSpeedPage.Desktop_FCP_Text.getText();
            System.out.println("FCP Value .....  " + FCPValue);
            PageSpeedExcelUtils.setCellValue(excelPath, FCPValue);
        }
    }
}
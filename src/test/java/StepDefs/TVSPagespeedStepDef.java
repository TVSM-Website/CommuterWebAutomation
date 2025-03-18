package StepDefs;

import Utils.PageSpeedExcelUtils;
import Utils.Utilities;
import Utils.WebDriverManager;
import com.azure.core.http.rest.Page;
import com.tvs.pages.HomePage;
import com.tvs.pages.PageSpeedPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.List;

import static Utils.ExplicitWait.visibilityofwebelement;

public class TVSPagespeedStepDef {
    private static final Logger log = LogManager.getLogger(TestRideStepDef.class);
    private WebDriver driver;
    private PageSpeedPage pageSpeedPage;
    public String url = "https://pagespeed.web.dev";
    public String excelpath = "src/test/Resources/TestData/PageSpeedOutput.xlsx";
    public static int rowcount = 0;
    public static String tvsurl;


    public TVSPagespeedStepDef() {
        this.driver = WebDriverManager.getDriver();
        pageSpeedPage = new PageSpeedPage(driver);
    }


    @Given("navigates to the Page Speed url")
    public void userNavigatedToPageSpeed() {
        driver.get(url);
    }

    @When("user navigated to home page and enters the TVS website url in {string} environment")
    public void userNavigatedToHomePageAndEntersVSPageurl(String tvsurl) throws IOException, InterruptedException {
        pageSpeedPage.ClickSearchUrl(tvsurl);
        // printing row heading in excel
        PageSpeedExcelUtils.excel_initiation(excelpath);
        PageSpeedExcelUtils.excel_createRow(excelpath,0);
        PageSpeedExcelUtils.setCellValue(excelpath, "Tvs Url");
        PageSpeedExcelUtils.setCellValue(excelpath," LCP Value - Mobile");
        PageSpeedExcelUtils.setCellValue(excelpath,"FCP Value - Mobile");
        PageSpeedExcelUtils.setCellValue(excelpath," LCP Value - Desktop");
        PageSpeedExcelUtils.setCellValue(excelpath,"FCP Value - Desktop");
        rowcount = rowcount +1;
        PageSpeedExcelUtils.excel_createRow( excelpath,rowcount);
        PageSpeedExcelUtils.setCellValue(excelpath,tvsurl);
    }

    @Then("prints the LCP and FCP values from Page Speed with TVS Website for mobiles")
    public void printLCPandFCPvaluesforMobiles() throws IOException {
        String LCPValue = pageSpeedPage.Mobile_LCP_Text.getText();
        System.out.println("Mobile LCP Value .....  " + LCPValue);
        PageSpeedExcelUtils.setCellValue(excelpath,LCPValue);
        String FCPValue = pageSpeedPage.Mobile_FCP_Text.getText();
        System.out.println("Mobile FCP Value .....  " + FCPValue);
        PageSpeedExcelUtils.setCellValue(excelpath,FCPValue);
        //PageSpeedExcelUtils.setCellValue(excelpath,"Mobiles");
    }

    @When("user clicks on Desktop and enters the TVS website url in {string} environment")
    public void UsersClicksDesktop(String url1) throws IOException, InterruptedException {
        tvsurl = url1;
        pageSpeedPage.ClickDesktop();
    }

    @Then("prints the LCP and FCP values from Page Speed with TVS Website for Desktop")
    public void printLCPandFCPvaluesforDesktop() throws IOException, InterruptedException {

        if (tvsurl.contains("book-a-ride")){
            String LCPValue = pageSpeedPage.BookRide_Desktop_LCP_Text.getText();
            System.out.println("LCP Value .....  " + LCPValue);
            PageSpeedExcelUtils.setCellValue(excelpath, LCPValue);
            String FCPValue = pageSpeedPage.BookRide_Desktop_FCP_Text.getText();
            System.out.println("FCP Value .....  " + FCPValue);
            PageSpeedExcelUtils.setCellValue(excelpath, FCPValue);
        }
        else {
            String LCPValue = pageSpeedPage.Desktop_LCP_Text.getText();
            System.out.println("LCP Value .....  " + LCPValue);
            PageSpeedExcelUtils.setCellValue(excelpath, LCPValue);
            String FCPValue = pageSpeedPage.Desktop_FCP_Text.getText();
            System.out.println("FCP Value .....  " + FCPValue);
            PageSpeedExcelUtils.setCellValue(excelpath, FCPValue);
        }
    }
}
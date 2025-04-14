package com.tvs.pages;

import Utils.ExplicitWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static Utils.ExplicitWait.waitForElementToBeClickable;

public class HomePage
{
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@class='cookie_but']/a")
    public WebElement AcceptCookie;

    @FindBy(xpath = "(//a[contains(text(),'Booking')])[1]")
    public static WebElement pageBookingBtn;

    @FindBy(xpath = "(//a[contains(text(),'Test Ride')])[1]")
    public static WebElement pageTestRideBtn;

    @FindBy(xpath = "(//a[contains(text(),'Dealer Locator')])[1]")
    public static WebElement pageDealerLocatorBtn;

    @FindBy(xpath = "//li[@class='childNav']//a[contains(text(),'Products')]")
    private WebElement Products;

    @FindBy(css = "li[class='activeTab'] a")
    private WebElement viewAllVehicles;

    @FindBy(xpath = "//select[@id='stateDrp']")
    private WebElement stateDropdown;



    //@FindBy(xpath = "//div[@id='Scooters']/div/ul/li/a")
    public By allScooters=By.xpath("//div[@id='mCSB_2_container']/div/ul/li/a");
    public By allMotorCycles=By.xpath("//div[@id='Motorcycles']/div/div/div/div/ul/li/a");
    public By allElectric=By.xpath("//div[@id='mCSB_3_container_wrapper']/div/div/ul/li/a");
    public By allMopeds=By.xpath("//div[@id='mCSB_9_container']/div/ul/li/a");
    public By allThreeWheelers=By.xpath("//div[@id='mCSB_10_container']/div/ul/li/a");

    public By langPopUp=By.xpath("//div[contains(@class,'langCont')]");

    @FindBy(xpath = "//div[@id='Motorcycles']//div[@class='product-wrap-lg']/h3")
    public WebElement motorCyclesCount;

    @FindBy(xpath = "//div[@id='Scooters']//div[@class='product-wrap-lg']/h3")
    public WebElement scootersCount;

    @FindBy(xpath = "//div[@id='Electric']//div[@class='product-wrap-lg']/h3")
    public WebElement electricCount;

    @FindBy(xpath = "//div[@id='Mopeds']//div[@class='product-wrap-lg']/h3")
    public WebElement mopedsCount;

    @FindBy(xpath = "//div[@id='Three-Wheelers']//div[@class='product-wrap-lg']/h3")
    public WebElement threeWheelersCount;



    @FindBy(xpath ="a[role='tab'][href='#Scooters']")
    public WebElement scooters;

    @FindBy(css ="a[role='tab'][href='#Scooters']")
    public WebElement productsScooters;
    @FindBy(css ="a[role='tab'][href='#Motorcycles']")
    public WebElement productsMotorCycles;
    @FindBy(css ="a[role='tab'][href='#Electric']")
    public WebElement productsElectric;
    @FindBy(css ="a[role='tab'][href='#Mopeds']")
    public WebElement productsMopeds;
    @FindBy(css ="a[role='tab'][href='#Three-Wheelers']")
    public WebElement productsThreeWheelers;

    @FindBy(xpath ="//li[@data-id='#set2']/a")
    private WebElement motorCycles;
    @FindBy(xpath ="//li[@data-id='#set3']/a")
    private WebElement mopeds;
    @FindBy(xpath ="//li[@data-id='#set4']/a")
    private WebElement electric;
    @FindBy(xpath ="//li[@data-id='#set5']/a")
    private WebElement threewheeler;

    @FindBy(xpath = "//img[contains(@src, 'Buy-vehicle.svg')]/parent::a")
    private WebElement BuyVehicle;

    @FindBy(xpath = "//div[@class='infoCont']/p[2]")
    private List<WebElement> stateoncards;

    @FindBy(xpath = "//div[@class='infoCont']/p[1]")
    private List<WebElement> vehicleName;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='motorcycles']//ul/li/a" )
    public List<WebElement> footerMotorCycles;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='ridesEvents']//ul/li/a" )
    public List<WebElement> footerRidesAndEvents;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='investors']/ul/li/a" )
    public List<WebElement> footerInvestors;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='dealerLocator']/ul/li/a" )
    public List<WebElement> footerTVSDealerLocator;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='aboutUs']/ul/li/a" )
    public List<WebElement> footerAboutUs;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='newsMedia']/ul/li/a" )
    public List<WebElement> footerNewsAndMedia;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='shop']/ul/li/a" )
    public List<WebElement> footerSHOP;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@class='footer-connect-app']/div/a" )
    public List<WebElement> footerTvsConnectApp;








    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='scooters']//ul/li/a" )
    public List<WebElement> footerScooters;


    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='moped']//ul/li/a" )
    public List<WebElement> footerMopeds;


    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='electricScooters']//ul/li/a" )
    public List<WebElement> footerElectricScooters;

    @FindBy(xpath = "//footer[@class='tvs-corporate-footer']//div[@id='threeWheeler']//ul/li/a" )
    public List<WebElement> footerThreeWheeler;


    @FindBy(xpath = "//div[@id='motorcycles']/ul/li")
    public List<WebElement> footerMotorCyclesCount;

    @FindBy(xpath = "//div[@id='ridesEvents']//ul/li/a")
    public List<WebElement> footerRidesAndEventsCount;

    @FindBy(xpath = "//div[@id='investors']/ul/li/a")
    public List<WebElement> footerInvestorsCount;

    @FindBy(xpath = "//div[@id='dealerLocator']/ul/li/a")
    public List<WebElement> footerTvsDealerLocatorCount;

    @FindBy(xpath = "//div[@id='aboutUs']/ul/li/a")
    public List<WebElement> footerAboutUsCount;

    @FindBy(xpath = "//div[@id='newsMedia']/ul/li/a")
    public List<WebElement> footerNewsAndMediaCount;

    @FindBy(xpath = "//div[@id='shop']/ul/li/a")
    public List<WebElement> footerShopCount;

    @FindBy(xpath = "//div[@class='footer-connect-app']/div/a")
    public List<WebElement> footerTvsConnectAppCount;





    @FindBy(xpath = "//div[@id='scooters']/ul/li")
    public List<WebElement> footerScooterCount;

    @FindBy(xpath = "//div[@id='moped']/ul/li")
    public List<WebElement> footerMopedCount;


    @FindBy(xpath = "//div[@id='electricScooters']/ul/li")
    public List<WebElement> footerElectricScootersCount;



    @FindBy(xpath = "//div[@id='threeWheeler']/ul/li")
    public List<WebElement> footerThreeWheelerCount;


    public int scootersCount()
    {
        String text=scootersCount.getText();
        String count = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }
    public int motorCyclesCount()
    {
        String text=motorCyclesCount.getText();
        String count = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }
    public int mopedsCount()
    {
        String text=mopedsCount.getText();
        String count = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }
    public int electricCount()
    {
        String text=electricCount.getText();
        String count = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }
    public int threeWheelersCount()
    {
        String text=threeWheelersCount.getText();
        String count = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(count);
    }



    public void ClickScootersTab() throws InterruptedException {
        Thread.sleep(3000);
        WebElement ClickScooterTab=waitForElementToBeClickable(driver,productsScooters,5);
        ClickScooterTab.click();
    }
    public void ClickViewAllVehicles()
    {
        viewAllVehicles.click();
    }

    public void ClickProducts() throws InterruptedException
    {
        Thread.sleep(3000);
        WebElement ClickProducts=waitForElementToBeClickable(driver, Products, 5);
        ClickProducts.click();
    }

    //public void

    public void ClickAcceptCookies()
    {
        AcceptCookie.click();
    }

    public  void ClickStateDropdown()
    {
        WebElement dropdown=driver.findElement(By.xpath("//select[@id='stateDrp']"));
        Select stateDropdown= new Select(dropdown);
        stateDropdown.selectByVisibleText("Karnataka");
    }

    public void  ClickScooter() throws InterruptedException
    {
        scooters.click();
        //JavascriptExecutor js= (JavascriptExecutor)driver;
        //js.executeScript("window.scrollBy(0, 400);"); // Scrolls down by 250 pixels
        Thread.sleep(2000);
        System.out.println("State in Scooter page: ");
        for (WebElement state : stateoncards) {
            String trimmedState = state.getText().trim();
            if (!trimmedState.isEmpty()) {
                System.out.println(trimmedState);
            }
        }

    }
    public void ClickScooters()
    {
        driver.findElement(allScooters).click();
    }


    public void  ClickMotorCycle() throws InterruptedException {
        motorCycles.click();
        //JavascriptExecutor js= (JavascriptExecutor)driver;
        //js.executeScript("window.scrollBy(0, 400);"); // Scrolls down by 250 pixels
        Thread.sleep(2000);
        System.out.println("State in MotorCycle page: ");
        for (WebElement state : stateoncards) {
            String trimmedState = state.getText().trim();
            if (!trimmedState.isEmpty()) {
                System.out.println(trimmedState);
            }
        }

    }

    public void  ClickMopeds() throws InterruptedException
    {
        mopeds.click();
        Thread.sleep(2000);
        System.out.println("State in Mopeds page: ");
        for (WebElement state : stateoncards) {
            String trimmedState = state.getText().trim();
            if (!trimmedState.isEmpty()) {
                System.out.println(trimmedState);
            }
        }
    }
    public void  ClickElectric() throws InterruptedException {
        electric.click();
        Thread.sleep(2000);
        System.out.println("State in Electric page: ");
        for (WebElement state : stateoncards) {
            String trimmedState = state.getText().trim();
            if (!trimmedState.isEmpty()) {
                System.out.println(trimmedState);
            }
        }
    }
    public void  ClickThreeWheeler() throws InterruptedException {
        threewheeler.click();
        Thread.sleep(2000);
        System.out.println("State in ThreeWheeler page: ");
        for (WebElement state : stateoncards) {
            String trimmedState = state.getText().trim();
            if (!trimmedState.isEmpty()) {
                System.out.println(trimmedState);
            }
        }
    }
    public void clickBuyVehicle()
    {
        waitForElementToBeClickable(driver, BuyVehicle, 10);
        BuyVehicle.click();
    }

    public void ClickMotorCyclesTab()
    {
        WebElement ClickMotorCyclesTab=waitForElementToBeClickable(driver,productsMotorCycles,10);
        ClickMotorCyclesTab.click();
    }
    public void ClickElectricTab()
    {
        WebElement ClickElectrictab=waitForElementToBeClickable(driver,productsElectric,10);
        ClickElectrictab.click();
    }

    public void ClickMopedsTab()
    {
        WebElement ClickMopedsTab=waitForElementToBeClickable(driver,productsMopeds,10);
        ClickMopedsTab.click();
    }

    public void ClickThreeWheelersTab()
    {
        WebElement Click3WheelerTab=waitForElementToBeClickable(driver,productsThreeWheelers,10);
        Click3WheelerTab.click();
    }

    public void langPopUpDisappear()
    {
        ExplicitWait.waitForLoaderToDisappear(driver, langPopUp, 10);
    }


    public static void clickBookingBtn(WebDriver driver) throws InterruptedException
    {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pageBookingBtn);


    }

    public static void clickTestRideBtn(WebDriver driver) throws InterruptedException
    {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pageTestRideBtn);

    }

    public static void clickDealerLocatorBtn(WebDriver driver) throws InterruptedException
    {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pageDealerLocatorBtn);

    }

    public int footerMotorCyclesCount()
    {
        return footerMotorCyclesCount.size();
    }

    public int footerRidesAndEventsCount()
    {
        return footerRidesAndEventsCount.size();
    }

    public int footerInvestorsCount()
    {
        return footerRidesAndEventsCount.size();
    }

    public int footerTvsDealerLocatorCount()
    {
        return footerTvsDealerLocatorCount.size();
    }
    public int footerAboutUsCount()
    {
        return footerAboutUsCount.size();
    }
    public int footerNewsAndMediaCount()
    {
        return footerNewsAndMediaCount.size();
    }
    public int footerShopCount()
    {
        return footerShopCount.size();
    }
    public int footerTvsConnectAppCount()
    {
        return footerTvsConnectAppCount.size();
    }







    public int footerScootersCount()
    {
        return footerScooterCount.size();
    }

    public int footerMopedsCount()
    {
        return footerMopedCount.size();
    }

    public int footerElectricScootersCount()
    {
        return footerElectricScootersCount.size();
    }


    public int footerThreeWheelerCount()
    {
        return footerThreeWheelerCount.size();
    }






}

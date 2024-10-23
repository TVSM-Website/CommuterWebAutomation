package com.tvs.pages;

import org.openqa.selenium.By;
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
    @FindBy(xpath = "//li[@class='childNav']//a[contains(text(),'Products')]")
    private WebElement Products;

    @FindBy(css = "li[class='activeTab'] a")
    private WebElement viewAllVehicles;

    @FindBy(xpath = "//select[@id='stateDrp']")
    private WebElement stateDropdown;

    //@FindBy(xpath = "//div[@id='Scooters']/div/ul/li/a")
    public By allScooters=By.xpath("//div[@id='Scooters']/div/ul/li/a");
    public By allMotorCycles=By.xpath("//div[@id='Motorcycles']/div/div/div/div/ul/li/a");
    public By allElectric=By.xpath("//div[@id='Electric']/div/ul/li/a");
    public By allMopeds=By.xpath("//div[@id='Mopeds']/div/ul/li/a");
    public By allThreeWheelers=By.xpath("//div[@id='Three-Wheelers']/div/ul/li/a");

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

    @FindBy(xpath = "//a[@class='testDriveDesk icon-bookVehicle']")
    private WebElement booking;

    @FindBy(xpath = "//div[@class='infoCont']/p[2]")
    private List<WebElement> stateoncards;

    @FindBy(xpath = "//div[@class='infoCont']/p[1]")
    private List<WebElement> vehicleName;


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



    public void ClickScootersTab()
    {
        WebElement ClickscooterTab=waitForElementToBeClickable(driver,productsScooters,10);
        ClickscooterTab.click();
    }
    public void ClickViewAllVehicles()
    {
        viewAllVehicles.click();
    }

    public void ClickProducts() throws InterruptedException
    {
        //Thread.sleep(3000);
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
    public void setBooking()
    {
        booking.click();
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



}

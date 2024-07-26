package com.tvs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class HomePage
{
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@class='cookie_but']/a")
    public WebElement AcceptCookie;
    @FindBy(xpath = "//a[contains(text(), 'Our Products')]")
    private WebElement ourProducts;

    @FindBy(xpath = "//select[@id='stateDrp']")
    private WebElement stateDropdown;

    @FindBy(xpath ="//li[@data-id='#set1']/a")
    private WebElement scooters;
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

    public void ClickOurProducts() throws InterruptedException {
        //Thread.sleep(3000);
        ourProducts.click();
    }

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




}

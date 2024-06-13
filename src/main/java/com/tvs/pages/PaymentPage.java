//package com.tvs.pages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.Select;
//
//public class PaymentPage
//{
//
//    WebDriver driver;
//
//    public PaymentPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }
//
//    @FindBy(xpath = "//div[@id='OPTNBK']//span[text()='Net Banking'][2]")
//    private static WebElement netbanking;
//
//    @FindBy(id="netBankingBank")
//    private static WebElement NetBankingDropdown;
//
//    @FindBy(xpath = "//a[@class='primary-button primary-button-bg radius4 SubmitBillShip'][1]")
//    private WebElement makePayment;
//
//    public static void clickNetBanking()
//    {
//        netbanking.click();
//    }
//
//    public static void selectNetBankingOption(String optionText)
//    {
//        Select dropdown = new Select(NetBankingDropdown);
//        dropdown.selectByVisibleText(optionText);
//    }
//
//    public void ClickMakePayment()
//    {
//        makePayment.click();
//    }
//
//
//}

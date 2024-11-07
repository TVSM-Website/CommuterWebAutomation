package StepDefs;

import Utils.WebDriverManager;
import com.tvs.pages.OneBot;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import Utils.ExplicitWait;
import Utils.Utilities;
import Utils.WebDriverManager;


public class OneBotStepDef {

    private WebDriver driver;
    public OneBot oneBot;

    public OneBotStepDef(){
        this.driver = WebDriverManager.getDriver();
        oneBot = new OneBot(driver);
    }

    @Given("navigate to the TVS Motor page in {string}, {string}")
    public void navigateToThePage(String environment, String url) throws InterruptedException {
        driver.get(url);
        System.out.println("Navigating to the TVS Motor page in " + environment + " at " + url);
        Thread.sleep(4000);
    }

    @When("user finds the OneBot icon on the page")
    public void oneBotIcon() {
        if (oneBot.isOneBotButtonDisplayed()) {
            System.out.println("OneBot icon is displayed on the page");
        }
        else {
            System.out.println("OneBot icon is not displayed on the page");
        }
    }

    @Then("click on the OneBot icon and verify if Chat with us is displayed")
    public void chatWithUs() throws InterruptedException {
        oneBot.OneBotButton.click();
        Thread.sleep(3000);
        if (oneBot.ChatWithUs.isDisplayed()) {
            System.out.println("Chat with us is displayed");
        }
        else {
            System.out.println("Chat with us is not displayed");
        }
    }

    @And("click on the Chat with us and verify if the chat window is displayed")
    public void chatWindow() throws InterruptedException {
        oneBot.ChatWithUs.click();
        Thread.sleep(3000);
        if (oneBot.ChatbotContentContainer.isDisplayed()) {
            System.out.println("Chat window is displayed");
        }
        else {
            System.out.println("Chat window is not displayed");
        }
    }

    @And("verify the welcome text and languages are displayed")
    public void languagesInChatWindow() {
        if (oneBot.ChatbotWelcomeText.isDisplayed()) {
            System.out.println("Welcome text is displayed");
        }
        else {
            System.out.println("Welcome text is not displayed");
        }
        if (oneBot.EnglishLanguage.isDisplayed()) {
            System.out.println("English language is displayed");
        }
        else {
            System.out.println("English language is not displayed");
        }
        if (oneBot.HindiLanguage.isDisplayed()) {
            System.out.println("Hindi language is displayed");
        }
        else {
            System.out.println("Hindi language is not displayed");
        }
    }

    @And("verify if able to select {string} and how may I address you is displayed")
    public void languageSelectionAndAddressText(String language) throws InterruptedException {
        if (language.equals("English")) {
            oneBot.EnglishLanguage.click();
            Thread.sleep(2000);
            if (oneBot.AddressYouEnglish.isDisplayed()) {
                System.out.println("How may I address you is displayed in English");
            }
            else {
                System.out.println("How may I address you is not displayed in English");
            }
        }
        else if (language.equals("Hindi")) {
            oneBot.HindiLanguage.click();
            Thread.sleep(2000);
            if (oneBot.AddressYouHindi.isDisplayed()) {
                System.out.println("मैं आपको कैसे संबोधित कर सकता हूँ? is displayed");
            }
            else {
                System.out.println("मैं आपको कैसे संबोधित कर सकता हूँ? is not displayed");
            }
        }
    }

    @And("verify if able to enter name and check the options displayed")
    public void nameAndOptions() throws InterruptedException {
        oneBot.ChatBoxTextArea.sendKeys("Test");
        oneBot.ChatBoxTextArea.sendKeys(Keys.ENTER);
        Thread.sleep(6000);
        System.out.println("Incorrect Name entered in the chat box");
        if (oneBot.IncompleteNameError.isDisplayed()) {
            System.out.println("It seems the name you provided is incomplete. Please make sure to enter your full name.");
        }
        else {
            System.out.println("Error message is not displayed");
        }
        oneBot.ChatBoxTextArea.sendKeys("June");
        oneBot.ChatBoxTextArea.sendKeys(Keys.ENTER);
        System.out.println("Correct Name entered in the chat box");
        Thread.sleep(6000);
        if(!oneBot.ChatBotButtons.isEmpty()) {
            System.out.println("Options displayed in the chat window");
            for(int i=0; i<oneBot.ChatBotButtons.size(); i++) {
                System.out.println(oneBot.ChatBotButtons.get(i).getText());
            }
        }
        else {
            System.out.println("Options not displayed in the chat window");
        }

    }
}

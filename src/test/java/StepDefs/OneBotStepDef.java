package StepDefs;

import Utils.WebDriverManager;
import com.tvs.pages.OneBot;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
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
       }

    @When("user finds the OneBot icon on the page")
    public void oneBotIcon() {
        Assert.assertTrue(oneBot.isOneBotButtonDisplayed());
    }

    @Then("click on the OneBot icon and verify if Chat with us is displayed")
    public void chatWithUs() throws InterruptedException {
        oneBot.OneBotButton.click();
        Thread.sleep(2000);
        Assert.assertTrue(oneBot.ChatWithUs.isDisplayed());
    }

    @And("click on the Chat with us and verify if the chat window is displayed")
    public void chatWindow() throws InterruptedException {
        oneBot.ChatWithUs.click();
        Thread.sleep(2000);
        Assert.assertTrue(oneBot.ChatbotContentContainer.isDisplayed());
    }

    @And("verify the welcome text and languages are displayed")
    public void languagesInChatWindow() {
        Assert.assertTrue(oneBot.ChatbotWelcomeText.isDisplayed());
        Assert.assertTrue(oneBot.EnglishLanguage.isDisplayed());
        Assert.assertTrue(oneBot.HindiLanguage.isDisplayed());
    }

    @And("verify if able to select {string} and how may I address you is displayed")
    public void languageSelectionAndAddressText(String language) throws InterruptedException {
        if (language.equals("English")) {
            oneBot.EnglishLanguage.click();
            Thread.sleep(2000);
            Assert.assertTrue(oneBot.AddressYouEnglish.isDisplayed());
        }
        else if (language.equals("Hindi")) {
            oneBot.HindiLanguage.click();
            Thread.sleep(2000);
            Assert.assertTrue(oneBot.AddressYouHindi.isDisplayed());
        }
    }

    @And("verify if able to enter name and check the options displayed")
    public void nameAndOptions() throws InterruptedException {
        oneBot.ChatBoxTextArea.sendKeys("Test");
        oneBot.ChatBoxTextArea.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        System.out.println("Incorrect Name entered in the chat box");
        Assert.assertTrue(oneBot.IncompleteNameError.isDisplayed());
        oneBot.ChatBoxTextArea.sendKeys("June");
        oneBot.ChatBoxTextArea.sendKeys(Keys.ENTER);
        System.out.println("Correct Name entered in the chat box");
        Thread.sleep(4000);
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

    @And("verify if able to close the Chat bot")
    public void closeTheChatBot() throws InterruptedException {
        oneBot.ChatBotCloseButton.click();
        Thread.sleep(2000);
        Assert.assertTrue(oneBot.CloseSessionText.isDisplayed());
        oneBot.ConfirmButton.click();
        Thread.sleep(2000);
        oneBot.SkipFeedback.click();
        System.out.println("Chat bot is closed");

    }
}

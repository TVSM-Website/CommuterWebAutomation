package StepDefs;

import Utils.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        // Initialize the WebDriver before each scenario
        WebDriverManager.getDriver();
    }

    @After
    public void tearDown() {
        // Quit the WebDriver after each scenario to prevent lingering sessions
        WebDriverManager.quitDriver();
    }
}

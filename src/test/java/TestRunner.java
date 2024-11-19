import StepDefs.Hooks;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/Resources/features",
        glue = {"StepDefs", "Hooks"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        },

         //tags = "@testRideCampaign or @testRide", //NtorqtestRide
        //tags = "@regression", //"@matchedElement or @Raider or @ApacheSeries",
        //tags="@Raider or @ApacheSeries",
        tags = "@OneBot", //"@regression",

        //tags ="@Products-scooters",

//          dryRun = true,

        monochrome = true

)

public class TestRunner {


}
import StepDefs.Hooks;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/Resources/features",
        glue = {"StepDefs", "Hooks"},
        plugin = {"pretty",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
        },

        //tags = "@testRideCampaign or @testRide", //NtorqtestRide
        //tags = "@regression", //"@matchedElement or @Raider or @ApacheSeries",
        //tags="@Raider or @ApacheSeries",
        //tags = "@Raider",AllVehicles

        tags ="@PageSpeed",

//          dryRun = true,

        monochrome = true

)

public class TestRunner {


}
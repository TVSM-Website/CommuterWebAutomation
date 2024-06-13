import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/Resources/features",
        glue = {"StepDefs"},
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        },

         //tags = "@testRideCampaign or @testRide", //NtorqtestRide
        tags = "@priceValidation",


//          dryRun = true,

        monochrome = true

)

public class TestRunner {


}
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

        //tags = "@PageNavigation_Scooter or @PageNavigation_Electric or @PageNavigation_Mopeds or @PageNavigation_Bike or @PageNavigation_Scooter or @PageNavigation_Electric or @PageNavigation_Mopeds or @PageNavigation_3Wheelers",
        tags = "@JupiterORP",
        //"@ORPMultiVehicles",Raider_ExPrice Raider_OnRoadPrice

//          dryRun = true,
        monochrome = true
)

public class TestRunner {


}
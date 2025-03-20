import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/TempDynamicCSV.feature", // Use temporary feature file
    glue = "stepDefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberTestRunner {

    @BeforeClass
    public static void setup() {
        FeatureFileTempManager.prepareTemporaryFeatureFile(); // Create temporary file before execution
    }

    @AfterClass
    public static void cleanup() {
        FeatureFileTempManager.deleteTemporaryFeatureFile(); // Delete temporary file after execution
    }
}

package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.TestContext;

public class Hooks {

    private final TestContext context;

    // PicoContainer injects the same TestContext the step classes get
    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        context.getDriver(); // starts a fresh browser for every scenario
    }

    @AfterStep
    public void screenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] png = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(png, "image/png", "failure-screenshot");
        }
    }

    @After
    public void tearDown() {
        context.quitDriver();
    }
}

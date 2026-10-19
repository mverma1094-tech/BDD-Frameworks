package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utils.ConfigReader;
import utils.TestContext;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        context.loginPage().open();
    }

    @Given("the user is logged in as a standard user")
    public void theUserIsLoggedInAsAStandardUser() {
        context.loginPage().open();
        context.loginPage().loginAs(ConfigReader.get("standard.user"), ConfigReader.get("password"));
        Assert.assertEquals(context.inventoryPage().getTitle(), "Products");
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWith(String username, String password) {
        context.loginPage().loginAs(username, password);
    }

    @Then("the user should land on the {string} page")
    public void theUserShouldLandOnPage(String expectedTitle) {
        Assert.assertEquals(context.inventoryPage().getTitle(), expectedTitle);
        Assert.assertTrue(context.inventoryPage().getCurrentUrl().contains("inventory"),
                "URL should contain 'inventory'");
    }

    @Then("an error message containing {string} should be displayed")
    public void anErrorMessageShouldBeDisplayed(String expectedText) {
        String actual = context.loginPage().getErrorMessage();
        Assert.assertTrue(actual.contains(expectedText),
                "Expected error to contain '" + expectedText + "' but was: " + actual);
    }
}

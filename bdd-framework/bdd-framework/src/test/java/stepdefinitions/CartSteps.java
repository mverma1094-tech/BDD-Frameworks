package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utils.TestContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    @When("the user adds {string} to the cart")
    public void theUserAddsToTheCart(String product) {
        context.inventoryPage().addToCart(product);
    }

    @When("the user adds the following products to the cart:")
    public void theUserAddsTheFollowingProducts(DataTable table) {
        for (String product : table.asList()) {
            context.inventoryPage().addToCart(product);
        }
    }

    @When("the user removes {string} from the cart")
    public void theUserRemovesFromTheCart(String product) {
        context.inventoryPage().removeFromCart(product);
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        Assert.assertEquals(context.inventoryPage().getCartCount(), expected);
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String product) {
        context.inventoryPage().openCart();
        Assert.assertEquals(context.cartPage().getTitle(), "Your Cart");
        Assert.assertTrue(context.cartPage().getItemNames().contains(product),
                "Cart items: " + context.cartPage().getItemNames());
    }

    @When("the user sorts products by {string}")
    public void theUserSortsProductsBy(String option) {
        context.inventoryPage().sortBy(option);
    }

    @Then("the product prices should be in ascending order")
    public void pricesAscending() {
        List<Double> actual = context.inventoryPage().getProductPrices();
        List<Double> sorted = new ArrayList<>(actual);
        Collections.sort(sorted);
        Assert.assertEquals(actual, sorted);
    }

    @Then("the product names should be in descending order")
    public void namesDescending() {
        List<String> actual = context.inventoryPage().getProductNames();
        List<String> sorted = new ArrayList<>(actual);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, sorted);
    }
}

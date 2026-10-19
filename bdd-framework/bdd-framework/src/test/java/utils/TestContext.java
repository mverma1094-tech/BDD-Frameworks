package utils;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

/**
 * One instance per scenario, injected by PicoContainer into Hooks and every step class.
 * Holds the shared WebDriver and lazily builds page objects.
 */
public class TestContext {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    public WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.createDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public LoginPage loginPage() {
        if (loginPage == null) loginPage = new LoginPage(getDriver());
        return loginPage;
    }

    public InventoryPage inventoryPage() {
        if (inventoryPage == null) inventoryPage = new InventoryPage(getDriver());
        return inventoryPage;
    }

    public CartPage cartPage() {
        if (cartPage == null) cartPage = new CartPage(getDriver());
        return cartPage;
    }
}

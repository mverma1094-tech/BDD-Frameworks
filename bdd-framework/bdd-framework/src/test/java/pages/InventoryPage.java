package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private final By pageTitle   = By.cssSelector("[data-test='title']");
    private final By itemNames   = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices  = By.cssSelector("[data-test='inventory-item-price']");
    private final By cartBadge   = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink    = By.cssSelector("[data-test='shopping-cart-link']");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    /** Sauce Demo button ids follow "add-to-cart-<product-name-in-kebab-case>". */
    public void addToCart(String productName) {
        click(By.id("add-to-cart-" + toKebab(productName)));
    }

    public void removeFromCart(String productName) {
        click(By.id("remove-" + toKebab(productName)));
    }

    public int getCartCount() {
        return isDisplayed(cartBadge) ? Integer.parseInt(getText(cartBadge)) : 0;
    }

    public void openCart() {
        click(cartLink);
    }

    public void sortBy(String visibleText) {
        new Select(waitForVisible(sortDropdown)).selectByVisibleText(visibleText);
    }

    public List<String> getProductNames() {
        waitForVisible(itemNames);
        return driver.findElements(itemNames).stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        waitForVisible(itemPrices);
        return driver.findElements(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    private String toKebab(String name) {
        return name.toLowerCase().trim().replace(" ", "-");
    }
}

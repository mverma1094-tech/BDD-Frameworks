package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By cartItemNames = By.cssSelector("[data-test='inventory-item-name']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public List<String> getItemNames() {
        return driver.findElements(cartItemNames).stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }
}

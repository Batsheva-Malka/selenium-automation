package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * HomePage - Page Object for the inventory/home page after login
 * Represents the main products page on saucedemo.com
 */
public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = "[data-test^='add-to-cart']")
    private List<WebElement> addToCartButtons;

    /**
     * Constructor - Initialize page elements
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if home page is displayed
     * @return true if products page is visible
     */
    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            return pageTitle.getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get page title text
     * @return Page title
     */
    public String getPageTitleText() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.getText();
    }

    /**
     * Get number of products displayed
     * @return Count of inventory items
     */
    public int getProductCount() {
        return inventoryItems.size();
    }

    /**
     * Click on shopping cart
     */
    public void clickShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLink));
        shoppingCartLink.click();
    }

    /**
     * Get cart badge count
     * @return Number of items in cart
     */
    public int getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Add first product to cart
     */
    public void addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons.get(0)));
            addToCartButtons.get(0).click();
        }
    }

    /**
     * Add product to cart by index
     * @param index Product index (0-based)
     */
    public void addProductToCartByIndex(int index) {
        if (index < addToCartButtons.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons.get(index)));
            addToCartButtons.get(index).click();
        }
    }

    /**
     * Get product name by index
     * @param index Product index
     * @return Product name
     */
    public String getProductName(int index) {
        if (index < productNames.size()) {
            return productNames.get(index).getText();
        }
        return "";
    }

    /**
     * Get product price by index
     * @param index Product index
     * @return Product price as string
     */
    public String getProductPrice(int index) {
        if (index < productPrices.size()) {
            return productPrices.get(index).getText();
        }
        return "";
    }

    /**
     * Click on a product to view details
     * @param index Product index
     */
    public void clickProduct(int index) {
        if (index < productNames.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(productNames.get(index)));
            productNames.get(index).click();
        }
    }

    /**
     * Open sidebar menu
     */
    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton));
        menuButton.click();
    }

    /**
     * Logout from the application
     */
    public void logout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();
    }

    /**
     * Check if cart has items
     * @return true if cart has items
     */
    public boolean hasItemsInCart() {
        try {
            return cartBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get all product names
     * @return List of product name strings
     */
    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }
}

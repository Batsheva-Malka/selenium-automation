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
 * ProductPage - Page Object for product details and cart pages
 * Handles product details view and shopping cart operations
 */
public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Product Details Page Elements
    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(css = "[data-test^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "[data-test^='remove']")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    // Cart Page Elements
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantities;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = "[data-test^='remove']")
    private List<WebElement> removeButtons;

    // Checkout Page Elements
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement orderCompleteHeader;

    @FindBy(className = "summary_total_label")
    private WebElement summaryTotal;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    /**
     * Constructor - Initialize page elements
     * @param driver WebDriver instance
     */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Product Details Methods
    /**
     * Get product name on details page
     * @return Product name
     */
    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productName));
        return productName.getText();
    }

    /**
     * Get product description
     * @return Product description
     */
    public String getProductDescription() {
        wait.until(ExpectedConditions.visibilityOf(productDescription));
        return productDescription.getText();
    }

    /**
     * Get product price
     * @return Product price
     */
    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOf(productPrice));
        return productPrice.getText();
    }

    /**
     * Add product to cart from details page
     */
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    /**
     * Remove product from cart
     */
    public void removeFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();
    }

    /**
     * Go back to products list
     */
    public void backToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton));
        backToProductsButton.click();
    }

    /**
     * Check if add to cart button is visible
     * @return true if add to cart button is visible
     */
    public boolean isAddToCartVisible() {
        try {
            return addToCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if remove button is visible
     * @return true if remove button is visible
     */
    public boolean isRemoveButtonVisible() {
        try {
            return removeButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Cart Page Methods
    /**
     * Get number of items in cart
     * @return Number of cart items
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Get cart item name by index
     * @param index Item index
     * @return Item name
     */
    public String getCartItemName(int index) {
        if (index < cartItemNames.size()) {
            return cartItemNames.get(index).getText();
        }
        return "";
    }

    /**
     * Get cart item price by index
     * @param index Item index
     * @return Item price
     */
    public String getCartItemPrice(int index) {
        if (index < cartItemPrices.size()) {
            return cartItemPrices.get(index).getText();
        }
        return "";
    }

    /**
     * Remove item from cart by index
     * @param index Item index
     */
    public void removeCartItem(int index) {
        if (index < removeButtons.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(index)));
            removeButtons.get(index).click();
        }
    }

    /**
     * Click checkout button
     */
    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
    }

    /**
     * Click continue shopping button
     */
    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueShoppingButton.click();
    }

    // Checkout Methods
    /**
     * Fill checkout information
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     */
    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
    }

    /**
     * Click continue button on checkout
     */
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    /**
     * Click finish button to complete order
     */
    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        finishButton.click();
    }

    /**
     * Get order complete message
     * @return Order complete header text
     */
    public String getOrderCompleteMessage() {
        wait.until(ExpectedConditions.visibilityOf(orderCompleteHeader));
        return orderCompleteHeader.getText();
    }

    /**
     * Check if order is complete
     * @return true if order complete page is displayed
     */
    public boolean isOrderComplete() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderCompleteHeader));
            return orderCompleteHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get summary total
     * @return Total price string
     */
    public String getSummaryTotal() {
        wait.until(ExpectedConditions.visibilityOf(summaryTotal));
        return summaryTotal.getText();
    }

    /**
     * Check if checkout error is displayed
     * @return true if error message is visible
     */
    public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get checkout error message
     * @return Error message text
     */
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    /**
     * Complete full checkout process
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     */
    public void completeCheckout(String firstName, String lastName, String postalCode) {
        clickCheckout();
        fillCheckoutInfo(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
    }
}

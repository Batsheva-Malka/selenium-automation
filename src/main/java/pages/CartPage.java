package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private By cartItemCount = By.cssSelector(".quantity-count");
    private By confirmationMessage = By.cssSelector(".add-to-cart-messages");
    private By cartIcon = By.cssSelector(".minicart-link");
    private By cartItems = By.cssSelector(".product-cart-wrapper.row");
    private By increaseQuantityButton = By.cssSelector(".quantity__counter-button.quantity__counter-plus");
    private By itemPrice = By.cssSelector(".pricing > p:nth-child(2)");
    private By itemQuantity = By.cssSelector(".quantity__counter-value");
    private By totalPrice = By.cssSelector(".cart-total__value.cart-total--grand.estimated-total");
    private By checkoutButton = By.cssSelector(".checkout__button");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage openCart() {
        scrollToTop();
        clickElement(cartIcon);
        waitForElement(cartItems);
        return this;
    }

    public CartPage increaseQuantityByIndex(int itemIndex) {
        List<WebElement> items = findElements(cartItems);
        if (itemIndex >= 0 && itemIndex < items.size()) {
            WebElement item = items.get(itemIndex);
            WebElement plusButton = item.findElement(increaseQuantityButton);
            waitForElement(plusButton);
            plusButton.click();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public int getCartItemCount() {
        try {
            String countText = findElement(cartItemCount).getText();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }

    // Get list of item prices
    public List<Double> getItemPrices() {
        List<Double> prices = new ArrayList<>();
        List<WebElement> items = findElements(cartItems);

        for (WebElement item : items) {
            try {
                String priceText = item.findElement(itemPrice).getText().trim();
                // Remove currency symbols and parse
                priceText = priceText.replaceAll("[^0-9.]", "");
                prices.add(Double.parseDouble(priceText));
            } catch (Exception e) {
                prices.add(0.0);
            }
        }
        return prices;
    }

    // Get list of item quantities
    public List<Integer> getItemQuantities() {
        List<Integer> quantities = new ArrayList<>();
        List<WebElement> items = findElements(cartItems);

        for (WebElement item : items) {
            try {
                String qtyText = item.findElement(itemQuantity).getText().trim();
                quantities.add(Integer.parseInt(qtyText));
            } catch (Exception e) {
                quantities.add(1);
            }
        }
        return quantities;
    }

    // Get total price from website
    public double getTotalPriceFromWebsite() {
        try {
            String totalText = findElement(totalPrice).getText().trim();
            totalText = totalText.replaceAll("[^0-9.]", "");
            return Double.parseDouble(totalText);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public CartPage clickCheckout() {
        scrollToTop();

        // Use JavaScript click to avoid interception by header elements
        WebElement checkoutBtn = findElement(checkoutButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);        // try {
        //     Thread.sleep(2000); // Wait for page change
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        return this;
    }
    // public boolean isConfirmationMessageDisplayed() {
    //         return isElementDisplayed(confirmationMessage);
    // }
}

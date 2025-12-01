package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    
    private By cartItemCount = By.cssSelector(".minicart-quantity");
    private By confirmationMessage = By.cssSelector(".add-to-cart-messages");
    
    public CartPage(WebDriver driver) {
        super(driver);
    }
    
    public int getCartItemCount() {
        try {
            String countText = findElement(cartItemCount).getText();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public boolean isConfirmationMessageDisplayed() {
        return isElementDisplayed(confirmationMessage);
    }
}
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CategoryPage extends BasePage {
    
    // YOUR ORIGINAL WORKING LOCATORS
    private By productCards = By.cssSelector(".product-tile-content");
    private By addToCartButton = By.cssSelector("button.add-to-cart");
    
    public CategoryPage(WebDriver driver) {
        super(driver);
    }
    
    public CategoryPage addProductToCartByIndex(int productIndex) {
        List<WebElement> products = driver.findElements(productCards);
 for (int i = productIndex; i < products.size(); i++) {
            WebElement product = products.get(i);
            
            try {
                WebElement addButton = wait.until(
                    ExpectedConditions.elementToBeClickable(product.findElement(addToCartButton))
                );
                
                String buttonText = addButton.getText().trim();
                if (buttonText.equalsIgnoreCase("Add to Cart")) {
                    addButton.click();
                    break;  // Success, exit loop
                }
            } catch (Exception e) {
                System.out.println("⚠ Could not add product " + i + ", trying next...");
            }
        }        
        return this;
    }
}
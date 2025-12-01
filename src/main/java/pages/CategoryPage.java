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
        WebElement product = products.get(productIndex);
        
        // YOUR ORIGINAL WAIT
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement addButton = wait.until(
            ExpectedConditions.elementToBeClickable(product.findElement(addToCartButton))
        );
        addButton.click();
        
        return this;
    }
}
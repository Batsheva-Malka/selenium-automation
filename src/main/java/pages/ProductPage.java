// package pages;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import java.time.Duration;
// import java.util.List;
// public  class ProductPage {
//     private WebDriver driver;
//     private  WebDriverWait wait;
//     private int productIndex;
//     private By productCards = By.cssSelector(".product-tile-content");
//     private By addToCartButton = By.cssSelector("button.add-to-cart");
//     public ProductPage(WebDriver driver, int productIndex) {
//         this.driver = driver;
//         this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//         this.productIndex = productIndex;
//     }
//     public void addToCart() {
//         List<WebElement> products = driver.findElements(productCards);
//         WebElement product = products.get(productIndex);
//         try {
//             Thread.sleep(2000); // Sleeps for 2 seconds
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }// Sleeps for 2000 milliseconds (2 seconds)
//         // Wait for 2 seconds to ensure the product is fully loaded
//         WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(product.findElement(addToCartButton)));
//         addButton.click();
//         // Optionally wait for the button text to change (or another confirmation)
//        // wait.until(driver.getPageSource().contains("The product has been added to your cart."));
//     }
// }
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private int productIndex;

    // Locators
    private By productCards = By.cssSelector(".product-tile-content");
    private By addToCartButton = By.cssSelector("button.add-to-cart");
    private By cartConfirmationMessage = By.cssSelector(".cart-confirmation"); // Adjust based on actual element

    public ProductPage(WebDriver driver, int productIndex) {
        super(driver);
        this.productIndex = productIndex;
    }

    // Add product to cart - returns CartPage for verification
    public ProductPage addToCart() {
        List<WebElement> products = findElements(productCards);
        WebElement product = products.get(productIndex);

        // Find and click add to cart button within the product
        WebElement addButton = product.findElement(addToCartButton);
        wait.until(driver -> addButton.isDisplayed());
        addButton.click();
        return this;
    }

    // Alternative: Stay on product page after adding
    public ProductPage addToCartAndStay() {
        addToCart();
        return this;
    }
}

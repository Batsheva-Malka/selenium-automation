package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public  class ProductPage {
    private WebDriver driver;
    private  WebDriverWait wait;
    private int productIndex;
    private By productCards = By.cssSelector(".product-tile-content");
    private By addToCartButton = By.xpath("//button[text()='ADD TO CART']");

    public ProductPage(WebDriver driver, int productIndex) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.productIndex = productIndex;
    }

    public void addToCart() {
        List<WebElement> products = driver.findElements(productCards);
        WebElement product = products.get(productIndex);

        WebElement addButton = product.findElement(addToCartButton);
        addButton.click();

        // Optionally wait for the button text to change (or another confirmation)
       // wait.until(driver.getPageSource().contains("The product has been added to your cart."));
    }
}
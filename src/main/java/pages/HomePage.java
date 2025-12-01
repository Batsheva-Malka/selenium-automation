// package pages;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import java.time.Duration;
// import java.util.List;
// import pages.ProductPage;
// public class HomePage   {
//     private WebDriver driver;
//     private  WebDriverWait wait;
//     private By shopMenu = By.id("Shop");
//     private By categoryItems = By.cssSelector(".secondary-navigation__button");
//     // Constructor
//     public HomePage(WebDriver driver) {
//         this.driver = driver;
//         this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//     }
//     public void openShopMenu() {
//         WebElement shopButton = wait.until(ExpectedConditions.elementToBeClickable(shopMenu));
//         shopButton.click();
//         // Optionally, wait for the menu to appear
//         try {
//             Thread.sleep(9000); // Sleeps for 2 seconds
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         wait.until(ExpectedConditions.visibilityOfElementLocated(categoryItems));
//     }
//     public ProductPage selectProductFromCategory(String categoryName, int productIndex) {
//         //openShopMenu();  // Open the shop menu first
//         // Find the category by its name and click it
//         List<WebElement> categories = driver.findElements(categoryItems);
//         for (WebElement category : categories) {
//             if (category.getText().equalsIgnoreCase(categoryName)) {
//                 category.click();
//                 break;
//             }
//         }
//         // Return a new instance of ProductPage for interacting with the products
//         return new ProductPage(driver, productIndex);
//     }
//     // Navigate to home page
// //    public void goToHomePage(String url) {
// //        driver.get(url);
// //    }
// //    // Get page title
// //    public String getPageTitle() {
// //        return driver.getTitle();
// //    }
// //    // Get current URL
// //    public String getCurrentUrl() {
// //        return driver.getCurrentUrl();
// //    }
//     // Find element by locator
// //    public WebElement findElement(By locator) {
// //        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
// //    }
// //
// //    // Find multiple elements by locator
// //    public List<WebElement> findElements(By locator) {
// //        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
// //    }
// }
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    // YOUR ORIGINAL WORKING LOCATORS
    // private By shopMenu = By.cssSelector("#Shop");
    private By categoryItems = By.cssSelector(".secondary-navigation__button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // public HomePage openShopMenu() {
    //     WebElement shopButton = wait.until(ExpectedConditions.elementToBeClickable(shopMenu));
    //     shopButton.click();
    //     // YOUR ORIGINAL WAIT
    //     try {
    //         Thread.sleep(2000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(categoryItems));
    //     return this;
    // }
    public void goToHomePage(String url) {
        driver.get(url);
    }

    public CategoryPage selectCategory(String categoryName) {
        List<WebElement> categories = driver.findElements(categoryItems);

        for (WebElement category : categories) {
            if (category.getText().equalsIgnoreCase(categoryName)) {
                category.click();
                break;
            }
        }

        return new CategoryPage(driver);
    }
}

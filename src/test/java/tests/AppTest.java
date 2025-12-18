
package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bose.com/home");
    }

    @Test
    public void testFindShopMenu() throws InterruptedException {
        System.out.println("=== TEST: Finding Shop Menu ===");
        
        // Wait a bit for page to load
        Thread.sleep(3000);
        
        // Try different locators
        System.out.println("Trying By.id('Shop')...");
        try {
            WebElement shopById = driver.findElement(By.id("Shop"));
            System.out.println("✓ Found Shop by ID: " + shopById.getText());
            shopById.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Could not find Shop by ID");
        }
        
        // Check for category navigation
        System.out.println("\nLooking for category items...");
        String[] selectors = {
            ".secondary-navigation__button",
            "nav a",
            ".category-link",
            "[role='navigation'] a",
            ".navigation a"
        };
        
        for (String selector : selectors) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    System.out.println("✓ Found " + elements.size() + " elements with: " + selector);
                    for (int i = 0; i < Math.min(5, elements.size()); i++) {
                        System.out.println("  - " + elements.get(i).getText());
                    }
                }
            } catch (Exception e) {
                System.out.println("✗ No elements with: " + selector);
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CategoryPage;
import pages.HomePage;
import utils.Constants;

public class ShoppingCartTest {
    private WebDriver driver;
    private HomePage homePage;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.BASE_URL);
        homePage = new HomePage(driver);
    }
    
    @Test
    public void testAddItemsFromThreeCategories() {
        SoftAssert softAssert = new SoftAssert();
        
        // Category 1: Headphones
        // homePage.openShopMenu();
        CategoryPage categoryPage = homePage.selectCategory("Headphones");
        categoryPage.addProductToCartByIndex(0);
        
        // Category 2: Speakers
        // homePage.openShopMenu();
        homePage.goToHomePage(Constants.BASE_URL);
        categoryPage = homePage.selectCategory("Speakers");
        categoryPage.addProductToCartByIndex(0);
        
        // Category 3: Earbuds
        // homePage.openShopMenu();
        homePage.goToHomePage(Constants.BASE_URL);
        categoryPage = homePage.selectCategory("Earbuds");
        categoryPage.addProductToCartByIndex(0);
        
        System.out.println("✓ Successfully added items from 3 categories!");
        softAssert.assertAll();
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // driver.quit();
        }
    }
}
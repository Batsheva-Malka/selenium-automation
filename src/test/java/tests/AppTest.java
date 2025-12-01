////package org.example;
////
////import org.testng.Assert;
////import org.testng.annotations.Test;
////
/////**
//// * Unit test for simple App.
//// */
////public class AppTest {
////
////    /**
////     * Rigorous Test :-)
////     */
////    @Test
////    public void testApp() {
////        Assert.assertTrue(true);
////    }
////}
//package tests;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//public class AppTest {
//    private WebDriver driver;
//
//    @BeforeMethod
//    public void setUp() {
//        // Setup ChromeDriver automatically
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }
//
//    @Test
//    public void testGoogleTitle() {
//        // Navigate to Google
//        driver.get("https://www.google.com");
//
//        // Get page title
//        String title = driver.getTitle();
//        System.out.println("Page title: " + title);
//
//        // Verify title contains "Google"
//        Assert.assertTrue(title.contains("Google"),
//                "Page title should contain 'Google'");
//
//        System.out.println("✓ Test passed: Google page loaded successfully!");
//    }
//
//    @Test
//    public void testGoogleUrl() {
//        // Navigate to Google
//        driver.get("https://www.google.com");
//
//        // Get current URL
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("Current URL: " + currentUrl);
//
//        // Verify URL contains "google.com"
//        Assert.assertTrue(currentUrl.contains("google.com"),
//                "URL should contain 'google.com'");
//
//        System.out.println("✓ Test passed: Google URL is correct!");
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        // Close browser after each test
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
//
///* How to run:
// * 1. Right-click on the class → Run As → TestNG Test
// * 2. Or right-click on project → Run As → Maven test
// * 3. Check console for test results
// * 4. Look for "test-output" folder for HTML reports
// */
//
package  tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;


import pages.HomePage;
import pages.ProductPage;

public class AppTest {
    private WebDriver driver;
    private HomePage homePage;

    //
    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver automatically
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        driver.get("https://www.bose.com/home");
    }
    // Set up the WebDriver (Chrome in this case) and initialize the HomePage
//    @BeforeClass
//    public void setUp() {
//        // Set path for your ChromeDriver (make sure to download it and place it in your project directory)
//       // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//
//        // Initialize the WebDriver (with options if needed)
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");  // Open browser in maximized window
//        driver = new ChromeDriver(options);
//
//        // Initialize HomePage object
//        homePage = new HomePage(driver);
//
//        // Navigate to the Home page
//        driver.get("https://www.bose.com/home");
//    }

    @Test
    public void testAddToCartFromHeadphonesCategory() {
        // Navigate to the 'Headphones' category and select a product
        ProductPage productPage = homePage.selectProductFromCategory("Headphones", 0);  // Choose first product in the Headphones category

        // Add the selected product to the cart
        productPage.addToCart();

        // Add a verification here to ensure the product was added successfully (can be based on cart count or a confirmation message)
        // For example, check if the cart count has increased by 1
//        String expectedText = "The product has been added to your cart.";  // Text you expect after adding product
//        boolean isProductAdded = driver.getPageSource().contains(expectedText);
//
//        // Verify that the product was added to the cart successfully
//        Assert.assertTrue(isProductAdded, "The product was not added to the cart!");
    }

    // Clean up after tests
    @org.testng.annotations.AfterClass
    public void tearDown() {
        // Close the browser after tests are completed
        driver.quit();
    }
}

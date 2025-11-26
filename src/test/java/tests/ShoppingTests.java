package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.ProductPage;
import utils.DriverManager;

/**
 * ShoppingTests - Test class for shopping cart functionality
 * Tests shopping cart operations using saucedemo.com
 * Includes screenshots before and after content changes
 */
public class ShoppingTests {

    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;

    // Test credentials for saucedemo.com
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver("chrome");
        loginPage = new LoginPage(DriverManager.getDriver());
        homePage = new HomePage(DriverManager.getDriver());
        productPage = new ProductPage(DriverManager.getDriver());
        
        // Navigate and login
        loginPage.navigateToLoginPage();
        loginPage.login(USERNAME, PASSWORD);
        
        // Verify login was successful
        Assert.assertTrue(homePage.isHomePageDisplayed(), 
            "Should be logged in and on home page");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Test adding a single product to cart
     */
    @Test(description = "Verify adding a product to cart updates cart badge")
    public void testAddProductToCart() {
        // Take screenshot before adding to cart
        DriverManager.takeScreenshot("testAddProductToCart", "before_add");

        // Verify cart is empty initially
        Assert.assertEquals(homePage.getCartBadgeCount(), 0,
            "Cart should be empty initially");

        // Get product name before adding
        String productName = homePage.getProductName(0);

        // Add first product to cart
        homePage.addFirstProductToCart();

        // Take screenshot after adding to cart
        DriverManager.takeScreenshot("testAddProductToCart", "after_add");

        // Verify cart badge shows 1 item
        Assert.assertEquals(homePage.getCartBadgeCount(), 1,
            "Cart should have 1 item after adding product");

        // Go to cart and verify product is there
        homePage.clickShoppingCart();
        
        // Take screenshot of cart
        DriverManager.takeScreenshot("testAddProductToCart", "cart_view");

        Assert.assertEquals(productPage.getCartItemCount(), 1,
            "Cart should have 1 item");
        Assert.assertEquals(productPage.getCartItemName(0), productName,
            "Cart should contain the added product");
    }

    /**
     * Test adding multiple products to cart
     */
    @Test(description = "Verify adding multiple products to cart")
    public void testAddMultipleProductsToCart() {
        // Take screenshot before adding products
        DriverManager.takeScreenshot("testAddMultipleProducts", "before_add");

        // Add first three products to cart
        homePage.addProductToCartByIndex(0);
        homePage.addProductToCartByIndex(1);
        homePage.addProductToCartByIndex(2);

        // Take screenshot after adding products
        DriverManager.takeScreenshot("testAddMultipleProducts", "after_add");

        // Verify cart badge shows 3 items
        Assert.assertEquals(homePage.getCartBadgeCount(), 3,
            "Cart should have 3 items after adding products");

        // Go to cart and verify
        homePage.clickShoppingCart();
        
        // Take screenshot of cart with multiple items
        DriverManager.takeScreenshot("testAddMultipleProducts", "cart_view");

        Assert.assertEquals(productPage.getCartItemCount(), 3,
            "Cart should have 3 items");
    }

    /**
     * Test removing a product from cart
     */
    @Test(description = "Verify removing a product from cart")
    public void testRemoveProductFromCart() {
        // Add product to cart first
        homePage.addFirstProductToCart();
        Assert.assertEquals(homePage.getCartBadgeCount(), 1,
            "Cart should have 1 item");

        // Go to cart
        homePage.clickShoppingCart();
        
        // Take screenshot before removing
        DriverManager.takeScreenshot("testRemoveProduct", "before_remove");

        // Remove the item
        productPage.removeCartItem(0);

        // Take screenshot after removing
        DriverManager.takeScreenshot("testRemoveProduct", "after_remove");

        // Verify cart is empty
        Assert.assertEquals(productPage.getCartItemCount(), 0,
            "Cart should be empty after removing product");
    }

    /**
     * Test complete checkout process
     */
    @Test(description = "Verify complete checkout process")
    public void testCompleteCheckout() {
        // Add a product to cart
        homePage.addFirstProductToCart();
        
        // Take screenshot of home page with product added
        DriverManager.takeScreenshot("testCompleteCheckout", "product_added");

        // Go to cart
        homePage.clickShoppingCart();
        
        // Take screenshot of cart
        DriverManager.takeScreenshot("testCompleteCheckout", "cart_view");

        // Proceed to checkout
        productPage.clickCheckout();

        // Take screenshot of checkout form
        DriverManager.takeScreenshot("testCompleteCheckout", "checkout_form");

        // Fill checkout information
        productPage.fillCheckoutInfo("John", "Doe", "12345");
        
        // Take screenshot after filling form
        DriverManager.takeScreenshot("testCompleteCheckout", "form_filled");

        // Continue to overview
        productPage.clickContinue();

        // Take screenshot of order summary
        DriverManager.takeScreenshot("testCompleteCheckout", "order_summary");

        // Complete the order
        productPage.clickFinish();

        // Take screenshot of order confirmation
        DriverManager.takeScreenshot("testCompleteCheckout", "order_complete");

        // Verify order is complete
        Assert.assertTrue(productPage.isOrderComplete(),
            "Order complete page should be displayed");
        Assert.assertEquals(productPage.getOrderCompleteMessage(), 
            "Thank you for your order!",
            "Order confirmation message should be correct");
    }

    /**
     * Test checkout with empty form
     */
    @Test(description = "Verify checkout validation with empty fields")
    public void testCheckoutValidation() {
        // Add a product and go to cart
        homePage.addFirstProductToCart();
        homePage.clickShoppingCart();
        productPage.clickCheckout();

        // Take screenshot before clicking continue with empty form
        DriverManager.takeScreenshot("testCheckoutValidation", "empty_form");

        // Try to continue without filling form
        productPage.clickContinue();

        // Take screenshot after validation error
        DriverManager.takeScreenshot("testCheckoutValidation", "validation_error");

        // Verify error is displayed
        Assert.assertTrue(productPage.isErrorDisplayed(),
            "Error message should be displayed for empty fields");
        Assert.assertTrue(productPage.getErrorMessage().contains("First Name is required"),
            "Error should indicate first name is required");
    }

    /**
     * Test viewing product details
     */
    @Test(description = "Verify viewing product details")
    public void testViewProductDetails() {
        // Get product name before clicking
        String expectedName = homePage.getProductName(0);

        // Take screenshot before clicking product
        DriverManager.takeScreenshot("testViewProductDetails", "before_click");

        // Click on first product
        homePage.clickProduct(0);

        // Take screenshot of product details
        DriverManager.takeScreenshot("testViewProductDetails", "product_details");

        // Verify product details are displayed
        Assert.assertEquals(productPage.getProductName(), expectedName,
            "Product name should match");
        Assert.assertFalse(productPage.getProductDescription().isEmpty(),
            "Product description should not be empty");
        Assert.assertFalse(productPage.getProductPrice().isEmpty(),
            "Product price should not be empty");
    }

    /**
     * Test adding product from details page
     */
    @Test(description = "Verify adding product to cart from details page")
    public void testAddFromDetailsPage() {
        // Click on first product
        homePage.clickProduct(0);

        // Take screenshot before adding
        DriverManager.takeScreenshot("testAddFromDetails", "before_add");

        // Verify add to cart button is visible
        Assert.assertTrue(productPage.isAddToCartVisible(),
            "Add to cart button should be visible");

        // Add to cart
        productPage.addToCart();

        // Take screenshot after adding
        DriverManager.takeScreenshot("testAddFromDetails", "after_add");

        // Verify button changed to remove
        Assert.assertTrue(productPage.isRemoveButtonVisible(),
            "Remove button should be visible after adding to cart");
    }

    /**
     * Test cart persistence after navigating
     */
    @Test(description = "Verify cart contents persist after navigation")
    public void testCartPersistence() {
        // Add products to cart
        homePage.addProductToCartByIndex(0);
        homePage.addProductToCartByIndex(1);

        // Take screenshot of products page with items in cart
        DriverManager.takeScreenshot("testCartPersistence", "products_with_cart");

        // Go to cart
        homePage.clickShoppingCart();
        Assert.assertEquals(productPage.getCartItemCount(), 2,
            "Cart should have 2 items");

        // Continue shopping
        productPage.continueShopping();

        // Take screenshot after returning to products
        DriverManager.takeScreenshot("testCartPersistence", "back_to_products");

        // Verify cart still has items
        Assert.assertEquals(homePage.getCartBadgeCount(), 2,
            "Cart should still have 2 items after navigating back");
    }

    /**
     * Test product count on home page
     */
    @Test(description = "Verify product count on home page")
    public void testProductCount() {
        // Take screenshot of products page
        DriverManager.takeScreenshot("testProductCount", "products_page");

        // Verify products are displayed
        int productCount = homePage.getProductCount();
        Assert.assertTrue(productCount > 0,
            "There should be products displayed on home page");
        Assert.assertEquals(productCount, 6,
            "saucedemo.com should have 6 products");
    }
}

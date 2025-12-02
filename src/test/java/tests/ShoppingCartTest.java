package tests;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import utils.Constants;
import utils.ExcelReporter;

public class ShoppingCartTest extends BaseTest {

    private HomePage homePage;
    private ExcelReporter reporter;

    @BeforeClass
    public void setUp() {
        homePage = new HomePage(driver);

    }

    @Test(priority = 1)
    public void testAddItemsFromThreeCategories() {
        SoftAssert softAssert = new SoftAssert();

        CategoryPage categoryPage = homePage.selectCategory("Headphones");
        categoryPage.addProductToCartByIndex(0);

        homePage.goToHomePage(Constants.BASE_URL);
        categoryPage = homePage.selectCategory("Speakers");
        categoryPage.addProductToCartByIndex(0);

        homePage.goToHomePage(Constants.BASE_URL);
        categoryPage = homePage.selectCategory("Earbuds");
        categoryPage.addProductToCartByIndex(0);

        CartPage cartPage = new CartPage(driver);
        cartPage.openCart().increaseQuantityByIndex(2);

        List<Double> prices = cartPage.getItemPrices();
        List<Integer> quantities = cartPage.getItemQuantities();

        double calculatedTotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
            calculatedTotal += prices.get(i);
        }

        double websiteTotal = cartPage.getTotalPriceFromWebsite();
        reporter = new ExcelReporter("Cart Price Validation");
        reporter.addCartData(prices, quantities, calculatedTotal, websiteTotal);

        System.out.println("\n=== CART SUMMARY ===");
        for (int i = 0; i < prices.size(); i++) {
            System.out.println("Item " + (i + 1) + ": $" + prices.get(i) + " x " + quantities.get(i)
                    + " = $" + (prices.get(i)));
        }
        System.out.println("Calculated Total: $" + calculatedTotal);
        System.out.println("Website Total: $" + websiteTotal);
        System.out.println("Match: " + (Math.abs(calculatedTotal - websiteTotal) < 0.01 ? "YES" : "NO"));

        softAssert.assertEquals(calculatedTotal, websiteTotal, 0.01,
                "Calculated total should match website total");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "testAddItemsFromThreeCategories")
    public void testCheckoutScreenshots() {
        // Cart is already populated from first test
        CartPage cartPage = new CartPage(driver);

        // Take screenshot BEFORE checkout
        cartPage.takeScreenshot("before_checkout_" + System.currentTimeMillis() + ".png");

        // Click checkout button
        cartPage.clickCheckout();

        // Take screenshot AFTER checkout
        cartPage.takeScreenshot("after_checkout_" + System.currentTimeMillis() + ".png");

        System.out.println("✓ Screenshots captured successfully!");
    }

    @AfterClass
    public void tearDown() {
        reporter.saveReport("test-reports/CartPriceValidation_"
                + System.currentTimeMillis() + ".xlsx");

        // if (driver != null) {
        //     driver.quit();
        // }
    }
}

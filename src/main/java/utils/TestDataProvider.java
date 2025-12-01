package utils;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    
    @DataProvider(name = "categoryProductData")
    public static Object[][] getCategoryProductData() {
        return new Object[][] {
            {"Headphones", 0},   // Category name, Product index
            {"Speakers", 1},
            {"Earbuds", 0}
        };
    }
    
    @DataProvider(name = "multipleProductsPerCategory")
    public static Object[][] getMultipleProductsData() {
        return new Object[][] {
            {"Headphones", new int[]{0, 1}},      // Add 2 products from Headphones
            {"Speakers", new int[]{0}},           // Add 1 product from Speakers
            {"Earbuds", new int[]{1, 2}}          // Add 2 products from Earbuds
        };
    }
}
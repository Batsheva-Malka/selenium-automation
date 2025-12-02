package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Constants;

public class BaseTest {
    protected static WebDriver driver;
    
    @BeforeSuite
    public void setUpSuite() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.BASE_URL);
        System.out.println("✓ Browser opened for entire test suite");
    }
    
    @AfterSuite
    public void tearDownSuite() {
        // if (driver != null) {
        //     driver.quit();
        //     System.out.println("✓ Browser closed after entire test suite");
        // }
    }
}
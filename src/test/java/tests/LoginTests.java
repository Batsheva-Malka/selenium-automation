package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import utils.DriverManager;

/**
 * LoginTests - Test class for login functionality
 * Tests login page using saucedemo.com
 */
public class LoginTests {

    private LoginPage loginPage;
    private HomePage homePage;

    // Test credentials for saucedemo.com
    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";
    private static final String LOCKED_USER = "locked_out_user";
    private static final String INVALID_USERNAME = "invalid_user";
    private static final String INVALID_PASSWORD = "wrong_password";

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver("chrome");
        loginPage = new LoginPage(DriverManager.getDriver());
        homePage = new HomePage(DriverManager.getDriver());
        loginPage.navigateToLoginPage();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Test successful login with valid credentials
     */
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testSuccessfulLogin", "before_login");

        // Perform login
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Wait for page to load and take screenshot after login
        DriverManager.takeScreenshot("testSuccessfulLogin", "after_login");

        // Verify successful login by checking home page
        Assert.assertTrue(homePage.isHomePageDisplayed(), 
            "Home page should be displayed after successful login");
        Assert.assertEquals(homePage.getPageTitleText(), "Products",
            "Page title should be 'Products'");
    }

    /**
     * Test login with invalid username
     */
    @Test(description = "Verify error message with invalid username")
    public void testInvalidUsername() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testInvalidUsername", "before_login");

        // Perform login with invalid username
        loginPage.login(INVALID_USERNAME, VALID_PASSWORD);

        // Take screenshot after login attempt
        DriverManager.takeScreenshot("testInvalidUsername", "after_login_error");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for invalid username");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Username and password do not match"),
            "Error message should indicate invalid credentials");
    }

    /**
     * Test login with invalid password
     */
    @Test(description = "Verify error message with invalid password")
    public void testInvalidPassword() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testInvalidPassword", "before_login");

        // Perform login with invalid password
        loginPage.login(VALID_USERNAME, INVALID_PASSWORD);

        // Take screenshot after login attempt
        DriverManager.takeScreenshot("testInvalidPassword", "after_login_error");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for invalid password");
    }

    /**
     * Test login with locked user
     */
    @Test(description = "Verify error message for locked out user")
    public void testLockedOutUser() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testLockedOutUser", "before_login");

        // Perform login with locked user
        loginPage.login(LOCKED_USER, VALID_PASSWORD);

        // Take screenshot after login attempt
        DriverManager.takeScreenshot("testLockedOutUser", "after_login_error");

        // Verify locked out error message
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for locked user");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("locked out"),
            "Error message should indicate user is locked out");
    }

    /**
     * Test login with empty username
     */
    @Test(description = "Verify error message with empty username")
    public void testEmptyUsername() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testEmptyUsername", "before_login");

        // Perform login with empty username
        loginPage.login("", VALID_PASSWORD);

        // Take screenshot after login attempt
        DriverManager.takeScreenshot("testEmptyUsername", "after_login_error");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for empty username");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Username is required"),
            "Error message should indicate username is required");
    }

    /**
     * Test login with empty password
     */
    @Test(description = "Verify error message with empty password")
    public void testEmptyPassword() {
        // Take screenshot before login
        DriverManager.takeScreenshot("testEmptyPassword", "before_login");

        // Perform login with empty password
        loginPage.login(VALID_USERNAME, "");

        // Take screenshot after login attempt
        DriverManager.takeScreenshot("testEmptyPassword", "after_login_error");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for empty password");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Password is required"),
            "Error message should indicate password is required");
    }

    /**
     * Test login page is displayed correctly
     */
    @Test(description = "Verify login page is displayed correctly")
    public void testLoginPageDisplayed() {
        // Take screenshot of login page
        DriverManager.takeScreenshot("testLoginPageDisplayed", "login_page");

        // Verify login page elements are displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page should be displayed");
    }

    /**
     * Test logout functionality
     */
    @Test(description = "Verify logout redirects to login page")
    public void testLogout() {
        // Login first
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        
        // Take screenshot after login
        DriverManager.takeScreenshot("testLogout", "after_login");

        // Verify we're on home page
        Assert.assertTrue(homePage.isHomePageDisplayed(),
            "Home page should be displayed after login");

        // Perform logout
        homePage.logout();

        // Take screenshot after logout
        DriverManager.takeScreenshot("testLogout", "after_logout");

        // Verify we're back on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page should be displayed after logout");
    }
}

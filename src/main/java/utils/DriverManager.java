package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DriverManager - Utility class for managing WebDriver instances
 * Implements singleton pattern for thread-safe driver management
 */
public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String SCREENSHOTS_DIR = "screenshots";

    /**
     * Initialize WebDriver with default Chrome browser
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver("chrome");
        }
        return driver.get();
    }

    /**
     * Initialize WebDriver with specified browser
     * @param browserName - chrome or firefox
     */
    public static void initializeDriver(String browserName) {
        WebDriver webDriver;

        switch (browserName.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                webDriver = new ChromeDriver(chromeOptions);
                break;
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        webDriver.manage().window().maximize();

        driver.set(webDriver);
    }

    /**
     * Quit the driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    /**
     * Take a screenshot and save it with a descriptive name
     * @param testName - Name of the test
     * @param description - Description of the screenshot (e.g., "before", "after")
     * @return Path to the saved screenshot
     */
    public static String takeScreenshot(String testName, String description) {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            return null;
        }

        // Create screenshots directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create screenshots directory: " + e.getMessage(), e);
        }

        // Generate timestamp for unique file name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = String.format("%s/%s_%s_%s.png", SCREENSHOTS_DIR, testName, description, timestamp);

        // Take screenshot
        File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        Path destPath = Paths.get(fileName);

        try {
            Files.copy(srcFile.toPath(), destPath);
            System.out.println("Screenshot saved: " + destPath.toAbsolutePath());
            return destPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + e.getMessage(), e);
        }
    }

    /**
     * Navigate to a URL
     * @param url - The URL to navigate to
     */
    public static void navigateTo(String url) {
        getDriver().get(url);
    }

    /**
     * Get current page title
     * @return Page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }
}

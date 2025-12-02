package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Increased timeout to 30 seconds for slow internet
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Click element with wait
    protected void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Wait for element visibility
    protected void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Find element with wait
    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Find multiple elements
    protected List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    // Wait for element to be clickable and return it
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElement(WebElement element) {
        wait.until(driver -> element.isDisplayed() && element.isEnabled());
    }

    // Check if element is displayed
    protected boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Scroll to top of page
    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void takeScreenshot(String fileName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("✓ Screenshot saved: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    // ===== GENERAL WAIT METHODS =====

    // General wait for element to be enabled and displayed
    protected void waitForElementEnabled(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(driver -> {
                try {
                    WebElement element = driver.findElement(locator);
                    return element.isEnabled() && element.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            System.out.println("Warning: Element " + locator + " may not be enabled");
        }
    }

    // General wait for any condition with custom predicate
    protected void waitForCondition(int timeoutSeconds, java.util.function.Predicate<WebDriver> condition) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(condition::test);
        } catch (Exception e) {
            // Timeout acceptable - condition not met
        }
    }

    // Wait for URL to contain specific text
    protected void waitForUrlContains(String urlFragment, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(driver -> driver.getCurrentUrl().contains(urlFragment));
        } catch (Exception e) {
            System.out.println("Warning: URL does not contain '" + urlFragment + "'");
        }
    }

    // Wait for any of multiple elements to be visible
    protected void waitForAnyElementVisible(int timeoutSeconds, By... locators) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(driver -> {
                for (By locator : locators) {
                    try {
                        if (driver.findElements(locator).size() > 0 && 
                            driver.findElements(locator).get(0).isDisplayed()) {
                            return true;
                        }
                    } catch (Exception e) {
                        // Continue checking other locators
                    }
                }
                return false;
            });
        } catch (Exception e) {
            // Timeout acceptable
        }
    }
}
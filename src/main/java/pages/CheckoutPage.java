package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    // Locators
    private By emailField = By.cssSelector("#email");
    private By firstNameField = By.cssSelector("#shippingFirstNamedefault");
    private By lastNameField = By.cssSelector("#shippingLastNamedefault");
    private By addressField = By.cssSelector("#shippingAddressOnedefault");
    private By cityField = By.cssSelector("#shippingAddressCitydefault");
    private By stateDropdown = By.cssSelector("#shippingStatedefault");
    private By zipField = By.cssSelector("#shippingZipCodedefault");
    private By phoneField = By.cssSelector("#shippingPhoneNumberdefault");
    private By countryDropdown = By.cssSelector("#shippingCountrydefault");
    private By continueToPaymentButton = By.cssSelector("#form-submit");

    // Error message locators
    private By emailError = By.cssSelector("#emailInvalidMessage");
    private By firstNameError = By.cssSelector("div#shippingAddressFirstName.invalid-feedback");
    private By phoneError = By.cssSelector("div#shippingAddressTelephoneNumber.invalid-feedback");
    private By zipError = By.cssSelector("div#shippingAddressZipCode.invalid-feedback");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ===== WAIT METHODS (using BasePage general methods) =====

    public CheckoutPage waitForValidationErrors() {
        // Use the general method from BasePage
        waitForAnyElementVisible(3, emailError, firstNameError, zipError, phoneError);
        return this;
    }

    public CheckoutPage waitForZipFieldEnabled() {
        // Use the general method from BasePage
        waitForElementEnabled(zipField, 5);
        return this;
    }

    public CheckoutPage waitForPageTransition() {
        // Use the general method from BasePage
        waitForCondition(10, driver -> 
            driver.getCurrentUrl().contains("payment") || 
            driver.getCurrentUrl().contains("billing") ||
            driver.getCurrentUrl().contains("review")
        );
        return this;
    }

    // ===== FORM FILLING METHODS WITH FLUENT INTERFACE =====

    public CheckoutPage fillEmail(String email) {
        findElement(emailField).clear();
        findElement(emailField).sendKeys(email);
        return this;
    }

    public CheckoutPage fillFirstName(String firstName) {
        findElement(firstNameField).clear();
        findElement(firstNameField).sendKeys(firstName);
        return this;
    }

    public CheckoutPage fillLastName(String lastName) {
        findElement(lastNameField).clear();
        findElement(lastNameField).sendKeys(lastName);
        return this;
    }

    public CheckoutPage fillAddress(String address) {
        findElement(addressField).clear();
        findElement(addressField).sendKeys(address);
        return this;
    }

    public CheckoutPage fillCity(String city) {
        findElement(cityField).clear();
        findElement(cityField).sendKeys(city);
        return this;
    }

    public CheckoutPage selectCountry(String country) {
        Select countrySelect = new Select(findElement(countryDropdown));
        countrySelect.selectByVisibleText(country);
        return this;
    }

    public CheckoutPage selectState(String state) {
        Select stateSelect = new Select(findElement(stateDropdown));
        stateSelect.selectByVisibleText(state);
        return this;
    }

    public CheckoutPage fillZip(String zip) {
        try {
            // Wait for field to be enabled first
            waitForElementEnabled(zipField, 5);
            
            // Try normal interaction first
            WebElement zipElement = findElement(zipField);
            
            // Scroll element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", zipElement);
            
            // Small wait for scroll animation using general wait method
            waitForCondition(1, driver -> true);
            
            // Try clicking to focus
            zipElement.click();
            zipElement.clear();
            zipElement.sendKeys(zip);
            
        } catch (Exception e) {
            // Fallback to JavaScript if normal interaction fails
            System.out.println("Using JavaScript fallback for ZIP field");
            WebElement zipElement = driver.findElement(zipField);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", zipElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", zipElement, zip);
        }
        
        return this;
    }

    public CheckoutPage fillPhone(String phone) {
        findElement(phoneField).clear();
        findElement(phoneField).sendKeys(phone);
        return this;
    }

    public CheckoutPage fillCompleteForm(String email, String firstName, String lastName,
                                          String address, String city, String country, String state, String zip, String phone) {
        return this.fillEmail(email)
                   .fillFirstName(firstName)
                   .fillLastName(lastName)
                   .fillAddress(address)
                   .fillCity(city)
                   .selectCountry(country)
                   .selectState(state)
                   .fillZip(zip)
                   .fillPhone(phone);
    }

    public CheckoutPage clickContinueToPayment() {
        clickElement(continueToPaymentButton);
        return this;
    }

    // ===== VALIDATION METHODS =====

    public boolean isEmailErrorDisplayed() {
        try {
            return findElements(emailError).size() > 0 && 
                   findElements(emailError).get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstNameErrorDisplayed() {
        try {
            return findElements(firstNameError).size() > 0 && 
                   findElements(firstNameError).get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isZipErrorDisplayed() {
        try {
            return findElements(zipError).size() > 0 && 
                   findElements(zipError).get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhoneErrorDisplayed() {
        try {
            return findElements(phoneError).size() > 0 && 
                   findElements(phoneError).get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnPaymentPage() {
        return driver.getCurrentUrl().contains("payment") || 
               driver.getCurrentUrl().contains("billing");
    }

    public String getEmailErrorText() {
        return findElement(emailError).getText();
    }

    public String getFirstNameErrorText() {
        return findElement(firstNameError).getText();
    }

    public String getZipErrorText() {
        return findElement(zipError).getText();
    }

    public String getPhoneErrorText() {
        return findElement(phoneError).getText();
    }
}
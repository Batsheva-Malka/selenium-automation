package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * RegistrationPage - Page Object for registration form with 6+ fields
 * Uses demoqa.com automation practice form
 */
public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    
    public static final String URL = "https://demoqa.com/automation-practice-form";

    // Form Elements - 6+ fields
    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(css = "label[for='gender-radio-1']")
    private WebElement genderMale;

    @FindBy(css = "label[for='gender-radio-2']")
    private WebElement genderFemale;

    @FindBy(css = "label[for='gender-radio-3']")
    private WebElement genderOther;

    @FindBy(id = "userNumber")
    private WebElement mobileField;

    @FindBy(id = "dateOfBirthInput")
    private WebElement dateOfBirthField;

    @FindBy(id = "subjectsInput")
    private WebElement subjectsField;

    @FindBy(css = "label[for='hobbies-checkbox-1']")
    private WebElement hobbySports;

    @FindBy(css = "label[for='hobbies-checkbox-2']")
    private WebElement hobbyReading;

    @FindBy(css = "label[for='hobbies-checkbox-3']")
    private WebElement hobbyMusic;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(id = "state")
    private WebElement stateDropdown;

    @FindBy(id = "city")
    private WebElement cityDropdown;

    @FindBy(id = "submit")
    private WebElement submitButton;

    // Modal Elements
    @FindBy(id = "example-modal-sizes-title-lg")
    private WebElement successModalTitle;

    @FindBy(className = "modal-body")
    private WebElement modalBody;

    @FindBy(id = "closeLargeModal")
    private WebElement closeModalButton;

    /**
     * Constructor - Initialize page elements
     * @param driver WebDriver instance
     */
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to registration page
     */
    public void navigateToRegistrationPage() {
        driver.get(URL);
    }

    /**
     * Enter first name
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    /**
     * Enter last name
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameField));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    /**
     * Enter email address
     * @param email Email address
     */
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    /**
     * Select gender
     * @param gender Gender option (Male, Female, Other)
     */
    public void selectGender(String gender) {
        switch (gender.toLowerCase()) {
            case "male":
                wait.until(ExpectedConditions.elementToBeClickable(genderMale));
                genderMale.click();
                break;
            case "female":
                wait.until(ExpectedConditions.elementToBeClickable(genderFemale));
                genderFemale.click();
                break;
            case "other":
                wait.until(ExpectedConditions.elementToBeClickable(genderOther));
                genderOther.click();
                break;
        }
    }

    /**
     * Enter mobile number
     * @param mobile Mobile number (10 digits)
     */
    public void enterMobile(String mobile) {
        wait.until(ExpectedConditions.visibilityOf(mobileField));
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    /**
     * Enter current address
     * @param address Current address
     */
    public void enterCurrentAddress(String address) {
        wait.until(ExpectedConditions.visibilityOf(currentAddressField));
        currentAddressField.clear();
        currentAddressField.sendKeys(address);
    }

    /**
     * Select hobby
     * @param hobby Hobby option (Sports, Reading, Music)
     */
    public void selectHobby(String hobby) {
        switch (hobby.toLowerCase()) {
            case "sports":
                wait.until(ExpectedConditions.elementToBeClickable(hobbySports));
                hobbySports.click();
                break;
            case "reading":
                wait.until(ExpectedConditions.elementToBeClickable(hobbyReading));
                hobbyReading.click();
                break;
            case "music":
                wait.until(ExpectedConditions.elementToBeClickable(hobbyMusic));
                hobbyMusic.click();
                break;
        }
    }

    /**
     * Click submit button
     */
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        // Scroll to submit button to make it visible
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", submitButton);
        // Wait for scroll animation to complete
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    /**
     * Fill complete registration form
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param gender Gender
     * @param mobile Mobile number
     * @param address Current address
     */
    public void fillRegistrationForm(String firstName, String lastName, String email,
                                     String gender, String mobile, String address) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectGender(gender);
        enterMobile(mobile);
        enterCurrentAddress(address);
    }

    /**
     * Submit registration form
     */
    public void submitForm() {
        clickSubmit();
    }

    /**
     * Check if success modal is displayed
     * @return true if success modal is visible
     */
    public boolean isSuccessModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModalTitle));
            return successModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get success modal title
     * @return Modal title text
     */
    public String getSuccessModalTitle() {
        wait.until(ExpectedConditions.visibilityOf(successModalTitle));
        return successModalTitle.getText();
    }

    /**
     * Close success modal
     */
    public void closeSuccessModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeModalButton));
        closeModalButton.click();
    }

    /**
     * Get first name field value
     * @return First name value
     */
    public String getFirstNameValue() {
        return firstNameField.getAttribute("value");
    }

    /**
     * Get last name field value
     * @return Last name value
     */
    public String getLastNameValue() {
        return lastNameField.getAttribute("value");
    }

    /**
     * Get email field value
     * @return Email value
     */
    public String getEmailValue() {
        return emailField.getAttribute("value");
    }

    /**
     * Get mobile field value
     * @return Mobile value
     */
    public String getMobileValue() {
        return mobileField.getAttribute("value");
    }

    /**
     * Get current address field value
     * @return Address value
     */
    public String getCurrentAddressValue() {
        return currentAddressField.getAttribute("value");
    }

    /**
     * Check if form is displayed
     * @return true if form elements are visible
     */
    public boolean isFormDisplayed() {
        try {
            return firstNameField.isDisplayed() && lastNameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if first name field is empty
     * @return true if first name is empty
     */
    public boolean isFirstNameEmpty() {
        return firstNameField.getAttribute("value").isEmpty();
    }

    /**
     * Check if field has validation error (red border)
     * @return true if validation error is shown
     */
    public boolean hasValidationError() {
        String classAttribute = firstNameField.getAttribute("class");
        return classAttribute != null && classAttribute.contains("is-invalid");
    }
}

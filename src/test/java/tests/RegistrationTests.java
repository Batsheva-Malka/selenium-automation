package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import utils.DriverManager;

/**
 * RegistrationTests - Test class for registration form with 6+ fields
 * Tests form validation and submission using demoqa.com
 * Includes screenshots before and after content changes
 */
public class RegistrationTests {

    private RegistrationPage registrationPage;

    // Test data for registration form
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String GENDER = "Male";
    private static final String MOBILE = "1234567890";
    private static final String ADDRESS = "123 Test Street, Test City, TC 12345";

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver("chrome");
        registrationPage = new RegistrationPage(DriverManager.getDriver());
        registrationPage.navigateToRegistrationPage();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Test successful form submission with all required fields
     */
    @Test(description = "Verify successful registration form submission")
    public void testSuccessfulRegistration() {
        // Take screenshot before filling form
        DriverManager.takeScreenshot("testSuccessfulRegistration", "before_fill");

        // Verify form is displayed
        Assert.assertTrue(registrationPage.isFormDisplayed(),
            "Registration form should be displayed");

        // Fill all 6 required fields
        registrationPage.fillRegistrationForm(FIRST_NAME, LAST_NAME, EMAIL, GENDER, MOBILE, ADDRESS);

        // Take screenshot after filling form
        DriverManager.takeScreenshot("testSuccessfulRegistration", "after_fill");

        // Verify fields are filled correctly
        Assert.assertEquals(registrationPage.getFirstNameValue(), FIRST_NAME,
            "First name should be filled");
        Assert.assertEquals(registrationPage.getLastNameValue(), LAST_NAME,
            "Last name should be filled");
        Assert.assertEquals(registrationPage.getEmailValue(), EMAIL,
            "Email should be filled");
        Assert.assertEquals(registrationPage.getMobileValue(), MOBILE,
            "Mobile should be filled");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after form submission
        DriverManager.takeScreenshot("testSuccessfulRegistration", "after_submit");

        // Verify success modal is displayed
        Assert.assertTrue(registrationPage.isSuccessModalDisplayed(),
            "Success modal should be displayed after form submission");
        Assert.assertEquals(registrationPage.getSuccessModalTitle(), 
            "Thanks for submitting the form",
            "Success modal title should be correct");
    }

    /**
     * Test form validation with empty first name
     */
    @Test(description = "Verify form validation for empty first name")
    public void testEmptyFirstName() {
        // Take screenshot before filling form
        DriverManager.takeScreenshot("testEmptyFirstName", "before_fill");

        // Fill form without first name
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.enterEmail(EMAIL);
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile(MOBILE);
        registrationPage.enterCurrentAddress(ADDRESS);

        // Take screenshot after partial fill
        DriverManager.takeScreenshot("testEmptyFirstName", "after_partial_fill");

        // Verify first name is empty
        Assert.assertTrue(registrationPage.isFirstNameEmpty(),
            "First name field should be empty");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submit attempt
        DriverManager.takeScreenshot("testEmptyFirstName", "after_submit_validation");

        // Verify form was not submitted (success modal not displayed)
        // Form should show validation error
        Assert.assertFalse(registrationPage.isSuccessModalDisplayed(),
            "Success modal should not be displayed with empty first name");
    }

    /**
     * Test form validation with empty last name
     */
    @Test(description = "Verify form validation for empty last name")
    public void testEmptyLastName() {
        // Take screenshot before filling form
        DriverManager.takeScreenshot("testEmptyLastName", "before_fill");

        // Fill form without last name
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterEmail(EMAIL);
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile(MOBILE);
        registrationPage.enterCurrentAddress(ADDRESS);

        // Take screenshot after partial fill
        DriverManager.takeScreenshot("testEmptyLastName", "after_partial_fill");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submit attempt
        DriverManager.takeScreenshot("testEmptyLastName", "after_submit_validation");

        // Verify form was not submitted
        Assert.assertFalse(registrationPage.isSuccessModalDisplayed(),
            "Success modal should not be displayed with empty last name");
    }

    /**
     * Test form with invalid email format
     */
    @Test(description = "Verify form validation for invalid email")
    public void testInvalidEmail() {
        // Take screenshot before filling form
        DriverManager.takeScreenshot("testInvalidEmail", "before_fill");

        // Fill form with invalid email
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.enterEmail("invalid-email");
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile(MOBILE);
        registrationPage.enterCurrentAddress(ADDRESS);

        // Take screenshot after filling form
        DriverManager.takeScreenshot("testInvalidEmail", "after_fill");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submit attempt
        DriverManager.takeScreenshot("testInvalidEmail", "after_submit_validation");
    }

    /**
     * Test form with invalid mobile number (less than 10 digits)
     */
    @Test(description = "Verify form validation for invalid mobile number")
    public void testInvalidMobile() {
        // Take screenshot before filling form
        DriverManager.takeScreenshot("testInvalidMobile", "before_fill");

        // Fill form with invalid mobile (too short)
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.enterEmail(EMAIL);
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile("12345"); // Only 5 digits
        registrationPage.enterCurrentAddress(ADDRESS);

        // Take screenshot after filling form
        DriverManager.takeScreenshot("testInvalidMobile", "after_fill");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submit attempt
        DriverManager.takeScreenshot("testInvalidMobile", "after_submit_validation");

        // Form should not submit with invalid mobile
        Assert.assertFalse(registrationPage.isSuccessModalDisplayed(),
            "Success modal should not be displayed with invalid mobile");
    }

    /**
     * Test selecting different gender options
     */
    @Test(description = "Verify gender selection options")
    public void testGenderSelection() {
        // Take screenshot before selection
        DriverManager.takeScreenshot("testGenderSelection", "before_selection");

        // Fill required fields and select Male
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.selectGender("Male");
        registrationPage.enterMobile(MOBILE);

        // Take screenshot after selecting Male
        DriverManager.takeScreenshot("testGenderSelection", "male_selected");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submission
        DriverManager.takeScreenshot("testGenderSelection", "after_submit");

        // Verify form submitted successfully
        Assert.assertTrue(registrationPage.isSuccessModalDisplayed(),
            "Success modal should be displayed");
    }

    /**
     * Test selecting hobby options
     */
    @Test(description = "Verify hobby selection options")
    public void testHobbySelection() {
        // Take screenshot before selection
        DriverManager.takeScreenshot("testHobbySelection", "before_selection");

        // Fill required fields
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile(MOBILE);

        // Select hobbies
        registrationPage.selectHobby("Sports");
        registrationPage.selectHobby("Reading");

        // Take screenshot after selecting hobbies
        DriverManager.takeScreenshot("testHobbySelection", "hobbies_selected");

        // Submit the form
        registrationPage.submitForm();

        // Take screenshot after submission
        DriverManager.takeScreenshot("testHobbySelection", "after_submit");

        // Verify form submitted successfully
        Assert.assertTrue(registrationPage.isSuccessModalDisplayed(),
            "Success modal should be displayed with hobbies selected");
    }

    /**
     * Test form field clearing
     */
    @Test(description = "Verify form fields can be cleared")
    public void testFieldClearing() {
        // Fill first name
        registrationPage.enterFirstName(FIRST_NAME);
        
        // Take screenshot after filling
        DriverManager.takeScreenshot("testFieldClearing", "after_fill");

        // Verify field is filled
        Assert.assertEquals(registrationPage.getFirstNameValue(), FIRST_NAME,
            "First name should be filled");

        // Clear and re-enter different value
        registrationPage.enterFirstName("Jane");

        // Take screenshot after changing value
        DriverManager.takeScreenshot("testFieldClearing", "after_change");

        // Verify field has new value
        Assert.assertEquals(registrationPage.getFirstNameValue(), "Jane",
            "First name should be updated to new value");
    }

    /**
     * Test all 6 fields are filled before submission
     */
    @Test(description = "Verify all 6 required fields are captured correctly")
    public void testAllFieldsFilled() {
        // Take screenshot before filling
        DriverManager.takeScreenshot("testAllFieldsFilled", "before_fill");

        // Fill all 6 fields
        registrationPage.enterFirstName(FIRST_NAME);
        registrationPage.enterLastName(LAST_NAME);
        registrationPage.enterEmail(EMAIL);
        registrationPage.selectGender(GENDER);
        registrationPage.enterMobile(MOBILE);
        registrationPage.enterCurrentAddress(ADDRESS);

        // Take screenshot after filling all fields
        DriverManager.takeScreenshot("testAllFieldsFilled", "all_fields_filled");

        // Verify all text fields are filled
        Assert.assertEquals(registrationPage.getFirstNameValue(), FIRST_NAME,
            "Field 1 - First name should be filled");
        Assert.assertEquals(registrationPage.getLastNameValue(), LAST_NAME,
            "Field 2 - Last name should be filled");
        Assert.assertEquals(registrationPage.getEmailValue(), EMAIL,
            "Field 3 - Email should be filled");
        // Field 4 - Gender is selected (radio button)
        Assert.assertEquals(registrationPage.getMobileValue(), MOBILE,
            "Field 5 - Mobile should be filled");
        Assert.assertEquals(registrationPage.getCurrentAddressValue(), ADDRESS,
            "Field 6 - Address should be filled");

        // Submit and verify success
        registrationPage.submitForm();

        // Take screenshot after submission
        DriverManager.takeScreenshot("testAllFieldsFilled", "after_submit");

        Assert.assertTrue(registrationPage.isSuccessModalDisplayed(),
            "Success modal should be displayed when all fields are filled");
    }

    /**
     * Test form page is accessible
     */
    @Test(description = "Verify registration form page is accessible")
    public void testFormPageAccessible() {
        // Take screenshot of form page
        DriverManager.takeScreenshot("testFormPageAccessible", "form_page");

        // Verify form is displayed
        Assert.assertTrue(registrationPage.isFormDisplayed(),
            "Registration form should be accessible and displayed");
    }
}

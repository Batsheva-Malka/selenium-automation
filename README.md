# Selenium Automation Project

A Maven-based Selenium WebDriver automation framework using TestNG and Page Object Model (POM) design pattern.

## Project Structure

```
src/
├── main/java/
│   ├── pages/
│   │   ├── LoginPage.java      # Login page interactions
│   │   ├── HomePage.java       # Home/Products page navigation
│   │   ├── ProductPage.java    # Product details and cart operations
│   │   └── RegistrationPage.java # Registration form (6+ fields)
│   └── utils/
│       └── DriverManager.java  # WebDriver management and screenshots
└── test/java/
    └── tests/
        ├── LoginTests.java      # Login functionality tests
        ├── ShoppingTests.java   # Shopping cart tests
        └── RegistrationTests.java # Form validation tests
```

## Test Websites Used

1. **saucedemo.com** - Shopping cart functionality
   - Login with credentials
   - Add/remove products from cart
   - Complete checkout process

2. **demoqa.com** - Registration form with 6+ fields
   - First Name, Last Name, Email
   - Gender, Mobile Number, Address
   - Form validation testing

## Features

- **Page Object Model (POM)** - Clean separation between test code and page elements
- **TestNG Framework** - Test organization and reporting
- **Automatic Driver Management** - Using WebDriverManager
- **Screenshot Capture** - Before and after content changes
- **Headless Browser Support** - For CI/CD integration

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (for non-headless testing)

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginTests

# Run specific test method
mvn test -Dtest=LoginTests#testSuccessfulLogin
```

## Screenshots

Screenshots are automatically saved to the `screenshots/` directory with descriptive names:
- `{testName}_{description}_{timestamp}.png`

Example: `testSuccessfulLogin_before_login_20231215_143022.png`

## Test Coverage

### Login Tests
- Successful login
- Invalid username/password
- Locked user
- Empty credentials
- Logout functionality

### Shopping Tests
- Add single/multiple products to cart
- Remove products from cart
- Complete checkout process
- Checkout validation
- Product details view

### Registration Tests
- Form submission with all fields
- Validation for empty fields
- Invalid email/mobile validation
- Gender and hobby selection
- All 6 fields capture verification
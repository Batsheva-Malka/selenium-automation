
package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private By categoryItems = By.cssSelector(".secondary-navigation__button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

   
    public void goToHomePage(String url) {
        driver.get(url);
    }

    public CategoryPage selectCategory(String categoryName) {
        List<WebElement> categories = driver.findElements(categoryItems);

        for (WebElement category : categories) {
            if (category.getText().equalsIgnoreCase(categoryName)) {
                category.click();
                break;
            }
        }

        return new CategoryPage(driver);
    }
}

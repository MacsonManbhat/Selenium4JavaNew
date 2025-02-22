package pageLocators;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LandingPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    By searchBox = By.xpath("//input[contains(@placeholder,'Search over') and contains(@placeholder, 'items')]");
    By searchButton = By.cssSelector("[data-testid='search button']");

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Using Duration for wait time
        this.actions = new Actions(driver);
    }

    public void enterSearchQuery(String query) {
        WebElement searchInputElement = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchInputElement);

        if (!searchInputElement.isDisplayed() || !searchInputElement.isEnabled()) {
            throw new ElementNotInteractableException("Element not interactable");
        }

        actions.moveToElement(searchInputElement).click().perform(); // Ensure element is interactable
        searchInputElement.sendKeys(query);
    }

    public void clickSearchButton() {
        WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButtonElement.click();
    }
}

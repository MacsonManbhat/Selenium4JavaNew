package pageLocators;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    By totalResultsHeader = By.cssSelector("[data-testid='non-cat-header']");
    By firstResultTitle = By.cssSelector("a.MuiTypography-root.MuiTypography-inherit.MuiLink-root.MuiLink-underlineHover.muiltr-h8u5m"); // will have to improve this selector
    By dropdownElement = By.xpath("//div[contains(text(),'Year')]");
    By yearFilterFrom = By.cssSelector("input#manufactureYearRange_min");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Using Duration for wait time
    }

    public int getTotalResults() {
        WebElement resultsHeaderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalResultsHeader));
        String resultsText = resultsHeaderElement.getText();

        String totalResultsStr = resultsText.replaceAll(".*of (\\d+) results.*", "$1");
        System.out.println(totalResultsStr);
        return Integer.parseInt(totalResultsStr);
    }

    public String getFirstResultTitle() {
        return driver.findElement(firstResultTitle).getText();
    }

    public void setYearFilterFrom(int year) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownElement));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);

        try {
            dropdown.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
        }

        WebElement yearInput = wait.until(ExpectedConditions.visibilityOfElementLocated(yearFilterFrom));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yearInput);

        yearInput.sendKeys(Keys.CONTROL + "a"); // Select all text
        yearInput.sendKeys(Keys.BACK_SPACE);
        yearInput.sendKeys(String.valueOf(year) + Keys.ENTER);
    }

    public void waitForResultsToUpdate(int initialResultsCount) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(totalResultsHeader, String.valueOf(initialResultsCount))));
    }
}

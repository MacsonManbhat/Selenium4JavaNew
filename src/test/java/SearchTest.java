import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import pageLocators.LandingPage;
import pageLocators.SearchResultsPage;

import java.time.Duration;

public class SearchTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);

    WebDriver driver;
    WebDriverWait wait;
    LandingPage landingPage;
    SearchResultsPage searchResultsPage;
    int totalResults;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.rbauction.com");
        test = extent.createTest("SearchTest");

        // Handle cookie banner with explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Using Duration for wait time
        WebElement cookieBannerButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("truste-consent-button")));
        if (cookieBannerButton.isDisplayed()) {
            cookieBannerButton.click();
        }

        landingPage = new LandingPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        test.log(Status.INFO, "Setup completed");
        logger.info("Setup completed");
    }

    @Test(priority = 1)
    public void performSearch() {
        landingPage.enterSearchQuery("Ford F-150");
        landingPage.clickSearchButton();
        test.log(Status.INFO, "Search performed");
        logger.info("Search performed");
    }

    @Test(priority = 2, dependsOnMethods = "performSearch")
    public void verifyTotalResults() {
        totalResults = searchResultsPage.getTotalResults();
        Assert.assertTrue(totalResults > 0, "Total results should be greater than 0");
        test.log(Status.INFO, "Search results have returned results greater than 0");
        logger.info("Search results have returned results greater than 0");
    }

    @Test(priority = 3, dependsOnMethods = "verifyTotalResults")
    public void verifyFirstResultTitle() {
        String firstResultTitle = searchResultsPage.getFirstResultTitle();
        Assert.assertTrue(firstResultTitle.matches(".*Ford F-?150.*"), "First result should contain 'Ford F-150' or 'Ford F150'");
        test.log(Status.INFO, "First title of search results includes Ford F-150 or Ford F150");
        logger.info("First title of search results includes Ford F-150 or Ford F150");
    }

    @Test(priority = 4, dependsOnMethods = "verifyFirstResultTitle")
    public void applyYearFilterAndVerifyResults() {
        searchResultsPage.setYearFilterFrom(2010);
        test.log(Status.INFO, "Search results filtered from 2010");
        logger.info("Search results filtered from 2010");

        // Hard wait to ensure search results update
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int filteredResults = searchResultsPage.getTotalResults();
        Assert.assertTrue(filteredResults < totalResults, "Filtered results should be less than total results");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        test.log(Status.INFO, "Teardown completed");
        logger.info("Teardown completed");
    }
}

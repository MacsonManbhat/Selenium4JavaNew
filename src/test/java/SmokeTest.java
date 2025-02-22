import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class SmokeTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SmokeTest.class);

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.rbauction.com");
        test = extent.createTest("SmokeTest");
        test.log(Status.INFO, "Browser opened and navigated to Ritchie Bros. Auctioneers website");
        logger.info("Browser opened and navigated to Ritchie Bros. Auctioneers website");
    }

    @Test
    public void testLandingPage() {
        String expectedTitle = "Ritchie Bros. Auctioneers: Heavy Equipment Auctions & Used Heavy Equipment For Sale";
        String actualTitle = driver.getTitle();
        logger.info("Actual page title: " + actualTitle);
        test.log(Status.INFO, "Actual page title: " + actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match expected value.");
        test.log(Status.PASS, "Page title verified successfully");
        logger.info("Page title verified successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            test.log(Status.INFO, "Browser closed");
            logger.info("Browser closed");
        }
    }
}

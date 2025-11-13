package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ElementActions;
import utils.Waits;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected Waits waits;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        waits = new Waits(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            log.info("Closing driver...");
            driver.quit();
            driver = null;
        }
    }
}
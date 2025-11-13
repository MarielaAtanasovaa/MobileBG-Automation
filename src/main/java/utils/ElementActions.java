package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import static org.junit.jupiter.api.Assertions.fail;

public class ElementActions {

    private static final Logger log = LogManager.getLogger(ElementActions.class);
    private final WebDriver driver;
    private static final String highlight = "background: lightGreen; border: solid 1px gray;";

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', '" + highlight + "')", element);
            js.executeScript("arguments[0].click();", element);
            log.info("Element '" + element + "' is clicked.");
        } catch (JavascriptException | StaleElementReferenceException ex) {
            fail("Click failed: " + ex.getMessage());
        }
    }
}
package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public Waits(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void waitForPageToLoad() {
        wait.until(webDriver -> {
            Object result = js.executeScript("return document.readyState");
            if (result == null) {
                return false;
            }
            return result.toString().equals("complete");
        });
    }
}
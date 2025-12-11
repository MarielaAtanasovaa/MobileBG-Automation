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
        waitForHTMLToLoad();
        waitForJQueryToDisappear();
    }

    private void waitForJQueryToDisappear() {
        wait.until(webDriver -> {
            try {
                Object result = js.executeScript(
                        "return window.jQuery ? jQuery.active : 0"
                );
                if (result != null) {
                    return Integer.parseInt(result.toString()) == 0;
                } else {
                    return false;
                }

            } catch (Exception e) {
                return true; // nothing to wait for if page has no jQuery
            }
        });
    }

    private void waitForHTMLToLoad() {
        wait.until(webDriver -> {
            Object result = js.executeScript("return document.readyState");
            if (result == null) {
                return false;
            }
            return result.toString().equals("complete");
        });
    }
}
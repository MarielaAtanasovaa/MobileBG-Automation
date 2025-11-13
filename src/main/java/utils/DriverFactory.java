package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class DriverFactory {
    private static final Properties props = new Properties();
    protected static final Logger log = LogManager.getLogger(DriverFactory.class);

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

    public static WebDriver createDriver() {
        String browser = props.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(props.getProperty("headless", "false"));
        WebDriver driver;
        log.info("Test will be executed on browser: {}", browser);
        log.info("Headless mode: {}", headless);
        log.info("URL in use: {}", getBaseUrl());


        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) {
                    fo.addArguments("--headless");
                }
                driver = new FirefoxDriver(fo);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                if (headless) {
                    co.addArguments("--headless=new");
                    co.addArguments("--disable-gpu");
                }
                driver = new ChromeDriver(co);
                break;
        }

        driver.manage().window().maximize();
        return driver;
    }

    public static String getBaseUrl() {
        return props.getProperty("url");
    }
}
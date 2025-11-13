package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.ElementActions;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ElementActions actions;

    @FindBy(id = "cookiescript_accept")
    private WebElement acceptCookies;

    @FindBy(className = "cat1")
    private WebElement carsCategory;

    @FindBy(id = "locatAll")
    private WebElement allRegions;

    @FindBy(className = "moreFilters")
    private WebElement moreFilters;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        this.actions = new ElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(DriverFactory.getBaseUrl());
    }

    public void acceptCookies() {
        actions.clickElement(acceptCookies);
    }

    public void selectCarsCategory() {
        actions.clickElement(carsCategory);
    }

    public void selectAllRegions() {
        actions.clickElement(allRegions);
    }

    public void selectMoreFilters() {
        actions.clickElement(moreFilters);
    }
}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementActions;

import java.time.Duration;

public class SearchPage {
    private final WebDriverWait wait;
    private final ElementActions actions;

    @FindBy(xpath = "//span[normalize-space()='4x4']/ancestor::label")
    private WebElement optionFourWheels;

    @FindBy(id = "akSearchMarkiArrow")
    private WebElement brandDropdown;

    @FindBy(id = "akSearchModeliArrow")
    private WebElement modelDropdown;

    @FindBy(xpath = "//a[contains(@class,'SEARCH_btn')]")
    private WebElement searchButton;

    public SearchPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        this.actions = new ElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectBrand(String brand) {
        wait.until(ExpectedConditions.elementToBeClickable(brandDropdown));
        actions.clickElement(brandDropdown);
        WebElement brandOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='a']/span[normalize-space()='" + brand + "']")));
        actions.clickElement(brandOption);
    }

    public void selectModel(String model) {
        wait.until(ExpectedConditions.elementToBeClickable(modelDropdown));
        actions.clickElement(modelDropdown);
        WebElement modelOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='akSearchModeli']//input[@type='checkbox' and @data-value='" + model + "']/ancestor::label")));
        actions.clickElement(modelOption);
    }
    public void search() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        actions.clickElement(searchButton);
    }
    public void selectFourWheelsOption() {
        wait.until(ExpectedConditions.elementToBeClickable(optionFourWheels));
        actions.clickElement(optionFourWheels);
    }
}
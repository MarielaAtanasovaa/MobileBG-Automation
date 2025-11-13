package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementActions;
import utils.Waits;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsPage {
    private static final Logger log = LogManager.getLogger(ResultsPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ElementActions actions;
    private int topCount;
    private int vipCount;
    private int bestCount;
    private int totalCount;
    private int displayedTotalCount;
    private final By allListedItems = By.cssSelector("div.ads2023 div.item:not(.fakti)");
    @FindBy(css = "a.saveSlink.next")
    private List<WebElement> nextButtons;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new ElementActions(driver);
        PageFactory.initElements(driver, this);
    }

    private void extractDisplayedTotalCount() {
        String text = driver.findElement(By.xpath("//div[contains(@style,'width:980px') and contains(@style,'font-weight:bold')]")).getText().trim();
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        String lastNumber = null;
        while (matcher.find()) {
            lastNumber = matcher.group();
        }
        if (lastNumber == null) {
            throw new IllegalStateException("Could not extract total count from text: " + text);
        }
        displayedTotalCount = Integer.parseInt(lastNumber);
    }

    private List<WebElement> getAllItemsOnCurrentPage() {
        Waits waits = new Waits(driver);
        waits.waitForPageToLoad();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allListedItems));
        return driver.findElements(allListedItems);
    }

    private boolean clickNextPageIfAvailable() {
        try {
            if (nextButtons == null || nextButtons.isEmpty()) {
                return false;
            }
            WebElement next = nextButtons.get(0);
            if (!next.isDisplayed() || !next.isEnabled()) {
                return false;
            }
            String currentUrl = driver.getCurrentUrl();
            actions.clickElement(next);
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void collectAllCountsFromDOM() {
        boolean hasNext = true;
        int pageNum = 1;
        while (hasNext) {
            List<WebElement> allItems = getAllItemsOnCurrentPage();
            totalCount += allItems.size();
            for (WebElement item : allItems) {
                String classes = item.getAttribute("class");
                if (classes.contains("TOP")) {
                    topCount++;
                    log.info("Found 'TOP' item on page: {}", pageNum);
                }
                if (classes.contains("VIP")) {
                    vipCount++;
                    log.info("Found 'VIP' item on page: {}", pageNum);
                }
                if (classes.contains("BEST")) {
                    bestCount++;
                    log.info("Found 'BEST' item on page: {}", pageNum);
                }
            }
            hasNext = clickNextPageIfAvailable();
            pageNum++;
        }
    }

    public int getTopCount() {
        return topCount;
    }

    public int getVipCount() {
        return vipCount;
    }

    public int getBestCount() {
        return bestCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getDisplayedTotalCount() {
        return displayedTotalCount;
    }

    public void readDisplayedTotalCount() {
        extractDisplayedTotalCount();
    }
}
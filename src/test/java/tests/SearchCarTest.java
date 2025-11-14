package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.ResultsPage;
import pages.SearchPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchCarTest extends BaseTest {
    @Test
    public void countFourWheelDriveGolfs() {
        HomePage home = new HomePage(driver);
        home.open();
        waits.waitForPageToLoad();

        home.acceptCookies();
        home.selectCarsCategory();
        home.selectAllRegions();
        home.selectMoreFilters();
        waits.waitForPageToLoad();

        SearchPage search = new SearchPage(driver);
        search.selectFourWheelsOption();
        search.selectBrand("VW");
        search.selectModel("Golf");
        search.search();
        waits.waitForPageToLoad();

        ResultsPage results = new ResultsPage(driver);
        results.readDisplayedTotalCount();
        int displayedResultsCount = results.getDisplayedTotalCount();
        results.collectAllCountsFromDOM();
        int resultsInDOM = results.getTotalCount();
        log.info("The total listed items are: {}", results.getTotalCount());
        log.info("The 'TOP' listed items are: {}", results.getTopCount());
        log.info("The 'VIP' listed items are: {}", results.getVipCount());
        log.info("The 'BEST' listed items are: {}", results.getBestCount());
        assertEquals(resultsInDOM, displayedResultsCount, "Mismatch between displayed total result count and the actual total number of items found in the DOM.");

    }
}

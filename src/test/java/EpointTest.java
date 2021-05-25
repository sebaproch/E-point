import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EpointTest {
    private WebDriver driver;
    EpointPage page;

    @BeforeClass
    public void beforeClass() throws Exception{

        System.setProperty( "webdriver.chrome.driver",
                Utils.CHROME_DRIVER_LOCATION );
        driver = new ChromeDriver();
        page = new EpointPage( driver );

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get(Utils.BASE_URL);
    }

    @Test(dataProvider = "input")
    void epTest(String inputData) throws Exception {

        // Task 1.1 Check Page Title
        Assert.assertEquals(page.getTitle(), page.PAGE_TITLE,"Page title do not match");
        page.clickLoupIcon();
        // Task 2.1 Check if popup is displayed
        Assert.assertTrue(page.checkIfPopupDisplayed(), "Search popup was not displayed");
        page.searchAndSendKeys( inputData );
        // Task 3.1 Check url if contains search text
        Assert.assertTrue(page.getUrl().contains(inputData), "Page url not contains text " + inputData + ".");
        // Task 3.2 Check if input contains search text
        Assert.assertEquals(page.searchInput.getAttribute("value"), inputData, "Page input contains wrong search text");
        // Task 3.3 Check if search results are displayed
        Assert.assertTrue(page.checkIfSearchResultsOnPage(), "Search results are not presented on page.");
        List<WebElement> firstPageTitles = page.pageTitles;
        page.goToPage( 1 );
        // Task 4.1 Check if input contains search text
        Assert.assertEquals(page.searchInput.getAttribute("value"), inputData, "Page input contains wrong search text");
        // Task 4.2 Check if search results are displayed
        Assert.assertTrue(page.checkIfSearchResultsOnPage(), "Search result are not presented on page.");
        List<WebElement> secondPageTitles = page.pageTitles;
        // Task 4.3 Compare results on page 1 and page 2
        Assert.assertTrue(page.compareResult(firstPageTitles,secondPageTitles), "Page titles on page 1 and page 2 are the same");
        page.clickCloseIcon();
        // Task 5.1 Check if input not contains search text
        Assert.assertEquals(page.searchInput.getAttribute("value"), "", "Page input contains search text");
        // Task 5.2 Check if search results are not displayed
        Assert.assertFalse(page.checkIfSearchResultsOnPage(), "Search result are presented on page.");
    }

    @AfterClass
    public void afterClass () throws Exception {
        Thread.sleep( 2000 );
        driver.quit();

    }
    @DataProvider(name = "input")
    public Object[][] getData () {
        return new Object[][]{
                {"test"}
        };
    }

}



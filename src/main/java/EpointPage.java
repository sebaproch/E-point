import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EpointPage extends PageObject {
    protected final String PAGE_TITLE = "Strona główna | e-point SA";
    @FindBy(xpath = "//div[@class='header header_with_additional_menu']/span[@class='search-icon']")
    private WebElement loupElement;

    @FindBy(xpath = "//div[@class='solr-search-modal']")
    private WebElement searchPopup;

    @FindBy(xpath = "//input[@class='gsc-input']")
    protected WebElement searchInput;

    @FindBy(xpath = "//div[@class='gsc-results-wrapper-nooverlay']")
    private WebElement searchResultsContainer;

    @FindBy(xpath = "//a[@class='gs-title']")
    protected List<WebElement> pageTitles;

    @FindBy(xpath = "//*[@id='query']")
    private WebElement finderElement;

    public EpointPage(WebDriver driver) {
        super(driver);
    }

    public Boolean checkIfPopupDisplayed() {
        return this.searchPopup.isDisplayed();
    }

    public Boolean checkIfSearchResultsOnPage() throws Exception {
        try {


            if (this.searchResultsContainer.isDisplayed() == false) {
                return false;
            }

            if (this.pageTitles.size() > 0) {
                return true;
            } else {
                return false;

            }
        }catch (Exception ex) {
            throw new Exception( "Coudn't check if results on the page" );
        }
    }

    public Boolean compareResult(List<WebElement> firstPageTitles, List<WebElement> secondPageTitles) throws Exception
    {
        try {


            Boolean state = true;
            Integer firstPageSize = firstPageTitles.size();
            Integer secondPageSize = secondPageTitles.size();

            Integer listSize = secondPageSize;
            if (firstPageSize <= secondPageSize) {
                listSize = firstPageSize;

            }

            for (int i = 0; i < listSize; i++) {
                if (firstPageTitles.get( i ).getText() == secondPageTitles.get( i ).getText()) {
                    state = false;
                }
            }
            return state;
        }catch (Exception ex) {
            throw new Exception( "Pages coudn't be compared");

        }
    }

    public void clickLoupIcon() {
        loupElement.click();
    }

    public void searchAndSendKeys(String query) {
        finderElement.sendKeys( query, Keys.ENTER );
    }

    public void goToPage(Integer pageNumber) throws Exception {
        WebElement pagingPage = driver.findElement( By.xpath( "//div[@class='gsc-cursor-page'][" + pageNumber + "]" ) );
        try {
            pagingPage.isDisplayed();
            pagingPage.click();
        } catch (Exception ex) {
            throw new Exception( "Paging element is not found on page" );
        }
    }

    public void clickCloseIcon() {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript( "window.location = '"+ this.getUrl()+"';" );
        js.executeScript("document.querySelector('span.gsc-clear-button',':before').click();");
    }
}


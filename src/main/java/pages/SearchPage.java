package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout for reliability
    }

    // LOCATORS
    private final By searchButton = By.xpath("/html/body/header/nav/ul/li[4]/a");
    private final By searchBar = By.id("autoComplete");
    private final By noResultsMessage = By.xpath("//*[@id=\"searchPage\"]/div[3]/div/label/span");

    // METHODS
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isSearchBarPresent() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            // Log or take screenshot if needed
            return false;
        }
    }

    public void searchForProduct(String productName) {
        try {
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
            searchInput.clear(); // Always clear before typing
            searchInput.sendKeys(productName);
            searchInput.sendKeys(Keys.ENTER);
        } catch (TimeoutException e) {
            throw new RuntimeException("Search bar not found or not visible", e);
        }
    }

    public String verifyNoResultsMessageIsDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
            return element.getText();
        } catch (TimeoutException e) {
            return "No results message not displayed";
        }
    }
}

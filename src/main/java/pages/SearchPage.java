package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {
   private WebDriver driver;
   private WebDriverWait wait;

   public SearchPage(WebDriver driver) {
       this.driver = driver;
       this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
   }

   //LOCATORS
   final private By SearchButton = By.xpath("/html/body/header/nav/ul/li[4]/a");
   final private By SearchBar = By.xpath("//*[@id='autoComplete']");
   final private By NoResultsMessage = By.xpath("//*[@id=\"searchPage\"]/div[3]/div/label/span");

   //METHODS
   public void clickSearchButton() { driver.findElement(SearchButton).click(); }

   public boolean isSearchBarPresent() {
       WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(SearchBar));
       return driver.findElement(SearchBar).isDisplayed();
   }
   public void SearchForProduct(String HP_ELITEBOOK_FOLIO) { //product name supposed to be without underscores
       WebElement SearchInput = driver.findElement(SearchBar);
       SearchInput.sendKeys(HP_ELITEBOOK_FOLIO);
       SearchInput.sendKeys(Keys.ENTER);
   }

    public String VerifyNoResultsMessageIsDisplayed() {
       WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(NoResultsMessage));
       return driver.findElement(NoResultsMessage).getText();
    }

}

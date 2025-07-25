package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TabletsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //CONSTRUCTOR
    public TabletsPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Locators
    final private By tabletsPageIden = By.xpath("//h1[normalize-space()='TRAVEL CONFIDENTLY AND IN STYLE']");
    final private By HPElitePad100 = By.xpath("//img[@id='16']");
    final private By HPEliteX21011 = By.xpath("//img[@id='17']");

    //METHODS

    public boolean CheckTabletsPageIsVisible() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(tabletsPageIden));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public HPElitePad1000G2TabletDetailsPage ClickOnFirstProduct(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(HPElitePad100));
        element.click();
        return new HPElitePad1000G2TabletDetailsPage(driver);
    }

    public HPElite_x2_1011G1TabletDetailsPage ClickOnSecondProduct(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(HPEliteX21011));
        element.click();
        return new HPElite_x2_1011G1TabletDetailsPage(driver);
    }


}

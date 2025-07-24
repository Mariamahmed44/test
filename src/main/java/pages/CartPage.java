package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //CONSTRUCTOR
    public CartPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //LOCATORS
    final private By EditBtn = By.xpath("//a[@class='edit ng-scope']");



    //METHODS
    public HPElitePad1000G2TabletDetailsPage ClickEdit(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(EditBtn));
        element.click();
        return new HPElitePad1000G2TabletDetailsPage(driver);
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    //CONSTRUCTOR
    public CartPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //LOCATORS
    By EditBtn = By.xpath("//a[@class='edit ng-scope']");



    //METHODS
    public HPElitePad1000G2TabletDetailsPage ClickEdit(){
        driver.findElement(EditBtn).click();
        return new HPElitePad1000G2TabletDetailsPage(driver);
    }
}

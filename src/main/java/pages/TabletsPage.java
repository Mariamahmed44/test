package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TabletsPage {
    WebDriver driver;
    WebDriverWait wait;

    //CONSTRUCTOR
    public TabletsPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Locators
    By HPElitePad100 = By.xpath("//img[@id='16']");
    By HPEliteX21011 = By.xpath("//img[@id='17']");

    //METHODS
    public HPElitePad1000G2TabletDetailsPage ClickOnTheProduct(){
        driver.findElement(HPElitePad100).click();
        return new HPElitePad1000G2TabletDetailsPage(driver);
    }

    public HPElite_x2_1011G1TabletDetailsPage ClickOnProduct(){
        driver.findElement(HPEliteX21011).click();
        return new HPElite_x2_1011G1TabletDetailsPage(driver);
    }


}

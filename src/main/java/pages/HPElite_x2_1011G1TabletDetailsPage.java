package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class HPElite_x2_1011G1TabletDetailsPage  {
    private WebDriver driver;
    private WebDriverWait wait;

    public HPElite_x2_1011G1TabletDetailsPage  (WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    //Locators
    final private By ProductPageIdentity = By.xpath("//h2[@class='roboto-regular product_specifications ng-scope']");
    final private By ProductAddToCart = By.xpath("//button[@name='save_to_cart']");
    final private By ProductName = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");


    //Methods
    public boolean VerifyEliteTabletG1PDP(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductPageIdentity));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public HPElite_x2_1011G1TabletDetailsPage EliteTabletG1ClickAddToCart() {
        driver.findElement(ProductAddToCart).click();

        return null;
    }
    public String VerifyEliteTabletG1Name() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductName));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();


    }

}

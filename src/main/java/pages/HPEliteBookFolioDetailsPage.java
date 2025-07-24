package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class HPEliteBookFolioDetailsPage {
   private WebDriver driver;
    private WebDriverWait wait;

    public HPEliteBookFolioDetailsPage (WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    final private By ProductPageIdentity = By.xpath("//h2[@class='roboto-regular product_specifications ng-scope']");
    final private By ProductAddToCart = By.xpath("//button[@name='save_to_cart']");
    final private By ProductName = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");


    //Methods
    public boolean VerifyEliteBookPDP(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductPageIdentity));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public void EliteBookClickAddToCart() {
        driver.findElement(ProductAddToCart).click();

    }
      public String VerifyEliteBookName() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductName));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();


    }
  
}

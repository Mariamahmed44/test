package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class HPElitePad1000G2TabletDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HPElitePad1000G2TabletDetailsPage (WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    //locators
    final private By ProductPageIdentity = By.xpath("//h2[@class='roboto-regular product_specifications ng-scope']");
    final private By ProductAddToCart = By.xpath("//button[@name='save_to_cart']");
    final private By ProductName = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");
    final private By TabletBreadCrumb = By.xpath("//a[@class='ng-binding']");
    final private By GrayColor = By.xpath("//span[@title='GRAY']");



    //Methods
    public boolean VerifyElitePadG2PDP(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductPageIdentity));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public void ElitePadG2ClickAddToCart() {
        driver.findElement(ProductAddToCart).click();

    }
    public String VerifyElitePadG2Name() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductName));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public TabletsPage ElitePadG2ClickTabletBreadCrumb() {
        driver.findElement(TabletBreadCrumb).click();
        return new TabletsPage(driver);
    }

    public void ClickOnGreyColorToEdit(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(GrayColor));
        element.click();
    }

    public CartPage ClickAddToCart(){
        driver.findElement(ProductAddToCart).click();
        return new CartPage(driver);
    }


}

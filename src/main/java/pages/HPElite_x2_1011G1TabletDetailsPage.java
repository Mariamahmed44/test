package pages;

import org.openqa.selenium.*;
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

    //locators
    final private By ProductPageIdentity = By.xpath("//h2[@class='roboto-regular product_specifications ng-scope']");
    final private By ProductAddToCart = By.xpath("//button[@name='save_to_cart']");
    final private By ProductName = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");
    final private By XIconForHpEliteX2inPopUp = By.xpath("//tbody/tr[1]/td[3]/div[1]/div[1]");
    final private By CartIcon = By.xpath("//a[@id='shoppingCartLink']//*[name()='svg']");
    final private By ProductNameFromPopUP = By.xpath("//h3[normalize-space()='HP ELITE X2 1011 G1 TABLET']");


    //Methods
    public boolean VerifyEliteTabletG1PDP(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductPageIdentity));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public void EliteTabletG1ClickAddToCart() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductAddToCart));
        element.click();

    }
    public String VerifyEliteTabletG1Name() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductName));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    public String VerifyEliteTabletG1NameInPopUp() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ProductNameFromPopUP));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    public void ClickOnXIconToDeleteHpEliteX2inPopUp(){
        wait.until(ExpectedConditions.elementToBeClickable(XIconForHpEliteX2inPopUp)).click();
    }
    public boolean VerifyEliteTabletG1Disappears() {
        try {
            // Wait until the element is no longer visible or removed from the DOM
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(ProductNameFromPopUP));
        } catch (TimeoutException e) {
            // Element did not disappear within the timeout period
            return false;
        }
    }

    public CartPage ClickOnCartIcon(){
        driver.findElement(CartIcon).click();
        return new CartPage(driver);
    }

}

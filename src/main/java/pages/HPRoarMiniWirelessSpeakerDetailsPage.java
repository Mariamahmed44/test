package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HPRoarMiniWirelessSpeakerDetailsPage.java {
    private WebDriver driver;
    private WebDriverWait wait;

    public HpSpeakerDetailsPage (WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By productName = By.cssSelector("h1.roboto-regular.screen768");
    private By quantity = By.name("quantity");
    private By colorFilter = By.xpath("(//*[@id=\"bunny\"])[2]");
    private By addToCart = By.name("save_to_cart");

    public String getProductName(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(productName));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
    public void putQuantity(){
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                quantity));
        quantityInput.clear();
        quantityInput.sendKeys("10");

    }
    public void clickOnColorFilter(){

        WebElement element= wait.until(ExpectedConditions.elementToBeClickable(colorFilter));
        element.click();
    }
    public CartPage addToCartButton(){
        WebElement detail= wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        detail.click();
        return new CartPage(driver);
    }
}

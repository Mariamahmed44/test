package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HPRoarMiniWirelessSpeakerDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HPRoarMiniWirelessSpeakerDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By productName = By.cssSelector("h1.roboto-regular.screen768");
    private final By quantity = By.name("quantity");
    private final By colorFilter = By.xpath("(//*[@id=\"bunny\"])[2]");
    private final By addToCart = By.name("save_to_cart");
    private final By checkOutBtn = By.xpath("//button[@id='checkOutPopUp']");
    private final By homeBtnInBreadCrumb = By.xpath("(//a[normalize-space()='HOME'])[1]");
    private final By productNameFromPopUp = By.xpath("//h3[normalize-space()='HP ROAR MINI WIRELESS SPEAKER']");
    final private By QuantityCheckInPopUp = By.xpath("//label[normalize-space()='QTY: 5']");

    public String getProductName() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return element.getText();
    }

    public String getPopupQuantity() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(QuantityCheckInPopUp));
        return element.getText();
    }

    public String getProductNameFromPopUp() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameFromPopUp));
        return element.getText();
    }

    public void putQuantity() {
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quantity));
        quantityInput.click();
        quantityInput.sendKeys("5");
    }

    public void clickOnColorFilter() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(colorFilter));
        element.click();
    }

    public CartPage addToCartButton() {
        WebElement detail = wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        detail.click();
        return new CartPage(driver);
    }

    public HomePage ClickOnHomeFromBreadCrumb(){
        driver.findElement(homeBtnInBreadCrumb).click();
        return new HomePage(driver);
    }


    public CheckOutPage ClickCheckout(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkOutBtn));
        element.click();
        return new CheckOutPage(driver);
    }
}

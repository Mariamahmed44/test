package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BeatsStudio2OverEarMatteBlackHeadPhonesDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //CONSTRUCTOR
    public BeatsStudio2OverEarMatteBlackHeadPhonesDetailsPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    //LOCATORS
    final private By QuantityInput = By.xpath("//input[@name='quantity']");
    final private By AddToCartBtn = By.cssSelector("button[name='save_to_cart']");
    final private By QuantityCheckInPopUp = By.xpath("//label[normalize-space()='QTY: 10']");
    final private By CheckoutBtnInPopUp = By.xpath("//button[@id='checkOutPopUp']");
    final private By productNameFromPopUp = By.xpath("//h3[normalize-space()='BEATS STUDIO 2 OVER-EAR MAT...']");

    //Methods
    public void setQuantity(String quantity) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(QuantityInput));
        element.click();
        element.sendKeys(quantity);
    }

    public String getProductNameFromPopUp() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameFromPopUp));
        return element.getText();
    }

    public void clickAddToCart(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(AddToCartBtn));
        waitForPopupToDisappear(driver);
        element.click();
    }

    public String getPopupQuantity() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(QuantityCheckInPopUp));
        return element.getText();
    }

    public CheckOutPage clickCheckout() {
        driver.findElement(CheckoutBtnInPopUp).click();
        return new CheckOutPage(driver);
    }

    public void waitForPopupToDisappear(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By popupLocator = By.cssSelector("div.PopUp");

        try {
            // Wait until the popup is invisible or removed
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupLocator));
            System.out.println("Popup closed.");
        } catch (TimeoutException e) {
            System.out.println("Popup did not close within timeout.");
        }
    }




}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //CONSTRUCTOR
    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //LOCATORS
    final private By CartPageIden = By.xpath("//h3[@class='roboto-regular center sticky fixedImportant ng-binding']");
    final private By EditBtn = By.xpath("//tbody/tr[1]/td[6]/span[1]/a[1]");


    //METHODS
    public boolean CheckCartPageIsVisible() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(CartPageIden));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public HPElitePad1000G2TabletDetailsPage ClickEdit() {
        waitForPopupToDisappear(driver);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(EditBtn));
        element.click();
        return new HPElitePad1000G2TabletDetailsPage(driver);
    }

    public void waitForPopupToDisappear(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By CartPopUp = By.cssSelector("li[data-ng-mouseenter='enterCart()'] ul li table");

        try {
            // Wait until the popup is invisible or removed
            wait.until(ExpectedConditions.invisibilityOfElementLocated(CartPopUp));
            System.out.println("Cart Popup closed.");
        } catch (TimeoutException e) {
            System.out.println("Cart Popup did not close within timeout.");
        }
    }
}

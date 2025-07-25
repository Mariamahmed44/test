package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpeakersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public SpeakersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By speakersPageIden = By.xpath("//h3[normalize-space(text())='SPEAKERS']");
    private final By colorFilter = By.id("accordionColor");
    private final By blackColorFilter = By.id("productsColors414141");
    private final By manufactureFilter = By.xpath("//h4[normalize-space(.)='MANUFACTURER']");
    private final By hpManufactureFilter = By.xpath("//input[@name='manufacturer_1' and @type='checkbox']");
    private final By hpSpeaker = By.xpath("//a[contains(text(),'HP Roar Mini Wireless Speaker')]");




    public boolean CheckSpeakersPageIsVisible() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(speakersPageIden));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public void clickOnColorFilter() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(colorFilter));
        element.click();
    }

    public void clickOnBlackColorFilter() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(blackColorFilter));
        element.click();
    }
    public void clickOnManufactureFilter() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(manufactureFilter));
        element.click();
    }

    public void clickOnHPManufactureFilter() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(hpManufactureFilter));
        element.click();
    }

    public HPRoarMiniWirelessSpeakerDetailsPage clickOnHpSpeaker() {
        WebElement detail = wait.until(ExpectedConditions.elementToBeClickable(hpSpeaker));
        detail.click();
        return new HPRoarMiniWirelessSpeakerDetailsPage(driver);
    }

    public String productName() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(hpSpeaker));
        return element.getText();
    }
}

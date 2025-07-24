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

    public SpeakersPage (WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By colorFilter = By.id("accordionColor");
    private By blackColorFilter =By.id("productsColors414141");
    private By hpSpeaker = By.xpath("//a[contains(text(),'HP Roar Mini Wireless Speaker')]");

    public void clickOnColorFilter(){

        WebElement element= wait.until(ExpectedConditions.elementToBeClickable(colorFilter));
        element.click();
    }
    public void clickOnBlackColorFilter(){
        WebElement element= wait.until(ExpectedConditions.elementToBeClickable(blackColorFilter));
        element.click();
    }
    public HpSpeakerDetailsPage clickOnHpSpeaker(){

        WebElement detail= wait.until(ExpectedConditions.elementToBeClickable(hpSpeaker));
        detail.click();
        return new HpSpeakerDetailsPage(driver);
    }
    public String productName(){
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(hpSpeaker));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();

    }

}


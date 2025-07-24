package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeadPhonesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //CONSTRUCTOR
    public HeadPhonesPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Locators
    final private By BeatsStudioHeadPhone = By.xpath("//img[@id='15']");

    //METHODS
    public BeatsStudio2OverEarMatteBlackHeadPhonesDetailsPage ClickOnProduct(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(BeatsStudioHeadPhone));
        element.click();
        return new BeatsStudio2OverEarMatteBlackHeadPhonesDetailsPage(driver);
    }



}

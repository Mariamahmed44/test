package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));

    }

    private By homePageIdent = By.xpath("//h3[text()='SPECIAL OFFER']");
    private By userButton = By.xpath("//a[@id='menuUserLink']");
    //private By loogedinusername = By.xpath("//span[@class='hi-user containMiniTitle ng-binding' and text()='"']");
    private By myaccountButton = By.xpath("//div[@id='loginMiniTitle']//label[text()='My account']");
    private By myordersButton = By.xpath("//div[@id='loginMiniTitle']//label[text()='My orders']");
    private By logoutButton = By.xpath("//div[@id='loginMiniTitle']//label[text()='Sign out']");
    private By speakersButton = By.xpath("//div[@id='speakersImg']");
    private By tabletsButton = By.xpath("//div[@id='tabletsImg']");
    private By headphonesButton = By.xpath("//div[@id='headphonesImg']");
    private By hpEliteBookFolioButton = By.xpath("//label[@id='details_10']");


    public boolean CheckHomePageIsVisible() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(homePageIdent));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public boolean CheckSpeakersButtonIsVisible() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(speakersButton));
        return wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
    }
//    public boolean isUserLoggedIn(String username) {
//        WebElement userText = wait.until(ExpectedConditions.visibilityOfElementLocated(loogedinusername));
//        return userText.getText().contains(username);
//    }
//To Check Home Page Is Visible

    public LoginPage ClickLoginOrSignUpButton(){

        wait.until(ExpectedConditions.elementToBeClickable(userButton)).click();
        return new LoginPage(driver);
    }
//Click On User Button If There Is No LoggedIn User

    public void ClickUserButton(){

        wait.until(ExpectedConditions.elementToBeClickable(userButton)).click();
    }
//To Access DropDown List If User Already Logged In


    public void ClickMyAccountButton(){

        wait.until(ExpectedConditions.elementToBeClickable(myaccountButton)).click();
    }

    public void ClickMyOrdersButton(){

        wait.until(ExpectedConditions.elementToBeClickable(myordersButton)).click();
    }

    public void ClickLogoutButton(){

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }


    public SpeakersPage clickSpeakersButton() {
        wait.until(ExpectedConditions.elementToBeClickable(speakersButton)).click();
        return new SpeakersPage(driver);
    }


    public TabletsPage clickTabletsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tabletsButton)).click();
        return new TabletsPage(driver);
      }

    public HeadPhonesPage clickHeadphonesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(headphonesButton)).click();
        return new HeadPhonesPage(driver);
      }

    public void ScrollToPopularItemsArea(){
        JavascriptExecutor js=(JavascriptExecutor)driver;
        //Define JavaScript Executor To Be Able To Add Scripts Like Scroll Down Etc.
        js.executeScript("window.scroll(0,1200)");
    }
    //JavaScript Code To Scroll To Popular Items Area

    public HPEliteBookFolioDetailsPage clickHPEliteBookFolioButton() {
        wait.until(ExpectedConditions.elementToBeClickable(hpEliteBookFolioButton)).click();
        return new HPEliteBookFolioDetailsPage(driver);
    }

}

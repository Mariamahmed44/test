package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * Page Object class for the login functionality
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By createNewAccountLink = By.xpath("//a[text()='CREATE NEW ACCOUNT']");
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By signInButton = By.id("sign_in_btn");
    private final By menuUserLink = By.id("menuUserLink");
    private final By popupForm = By.cssSelector(".PopUp");
    private final By loader = By.cssSelector(".loader");

    /**
     * Constructor initializes WebDriver and WebDriverWait.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public CreateAccountPage ClickOnRegisterBtn() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(createNewAccountLink));
        element.click();
        return new CreateAccountPage(driver);

    }

    /**
     * Waits for the loader to disappear from the page.
     */
    private void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (TimeoutException ignored) {
        }
    }

    public void waitForPopupToDisappear(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By popupLocator = By.cssSelector("div.PopUp");

        try {
            // Wait until the popup is invisible or removed
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupLocator));
            System.out.println("Popup closed.");
        } catch (TimeoutException e) {
            System.out.println("Popup did not close within timeout.");
        }
    }

    public void waitForLoginToClose(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By loginForm = By.cssSelector("div[ng-show='loginSection']"); // login modal container

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loginForm));
            System.out.println("Login form closed.");
        } catch (TimeoutException e) {
            System.out.println("Login form did not close within timeout.");
        }
    }
    /**
     * Navigates to the homepage and waits until the page is loaded.
     */
    public void navigateToWebsite() {
        driver.get("https://advantageonlineshopping.com/#/");
        wait.until(ExpectedConditions.titleContains("Advantage"));
    }

    /**
     * Opens the login popup by clicking the user icon.
     */
    public void openLoginForm() {
        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(menuUserLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupForm));
    }

    /**
     * Enters the provided username.
     */
    public void enterUsername(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /**
     * Enters the provided password.
     */
    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Submits the login form by clicking the sign-in button.
     * Falls back to JavaScript click if normal click fails.
     */
    public HomePage clickLoginButton() {

            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        waitForLoginToClose(driver);
        waitForPopupToDisappear(driver);
        return new HomePage(driver);
    }
}

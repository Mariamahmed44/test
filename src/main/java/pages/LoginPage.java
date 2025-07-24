package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * Page Object class for the login functionality using
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
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

    /**
     * Waits for the loader to disappear from the page.
     */
    private void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (TimeoutException ignored) {
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
    public void clickLoginButton() {
        waitForLoaderToDisappear();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        } catch (WebDriverException e) {
            WebElement button = driver.findElement(signInButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }
}

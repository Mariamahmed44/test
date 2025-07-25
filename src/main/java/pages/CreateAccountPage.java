package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * Page Object class for the "Create New Account" functionality
 */
public class CreateAccountPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /**
     * Constructor initializes driver and wait.
     */
    public CreateAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locators
    private final By usernameInput = By.name("usernameRegisterPage");
    private final By emailInput = By.name("emailRegisterPage");
    private final By passwordInput = By.name("passwordRegisterPage");
    private final By confirmPasswordInput = By.name("confirm_passwordRegisterPage");
    private final By firstNameInput = By.name("first_nameRegisterPage");
    private final By lastNameInput = By.name("last_nameRegisterPage");
    private final By phoneInput = By.name("phone_numberRegisterPage");
    private final By countryDropdown = By.name("countryListboxRegisterPage");
    private final By cityInput = By.name("cityRegisterPage");
    private final By addressInput = By.name("addressRegisterPage");
    private final By stateInput = By.name("state_/_province_/_regionRegisterPage");
    private final By postalCodeInput = By.name("postal_codeRegisterPage");
    private final By agreeCheckbox = By.name("i_agree");
    private final By registerButton = By.id("register_btn");
    private final By registerVisibility = By.xpath("//h3[normalize-space()='CREATE ACCOUNT']");


    /**
     * Fills in all required fields of the registration form.
     */
    public void fillRegistrationForm(
            String username, String email, String password,
            String firstName, String lastName, String phone,
            String country, String city, String address,
            String state, String postalCode) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(confirmPasswordInput).sendKeys(password);
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(phoneInput).sendKeys(phone);

        WebElement countryElement = driver.findElement(countryDropdown);
        new Select(countryElement).selectByVisibleText(country);

        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(postalCodeInput).sendKeys(postalCode);
    }

    /**
     * Selects the "I Agree" checkbox if not already selected.
     */
    public void agreeToTerms() {
        WebElement checkbox = driver.findElement(agreeCheckbox);
            checkbox.click();
    }

    /**
     * Clicks the register button to submit the form.
     */
    public void submitRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    /**
     * Verifies if registration was successful by checking the final URL.
     */
    public String isRegistrationSuccessful() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(registerVisibility));
        return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
    }
}

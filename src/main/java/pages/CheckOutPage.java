package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;


import java.time.Duration;

public class CheckOutPage {
    WebDriver driver;
    WebDriverWait wait;

    //CONSTRUCTOR
    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    //--------------Locator------------------

    // locate "shipping details"in the "Order payment" page
    final By shippingDetailsPartLocator = By.xpath("//label[text()='1. SHIPPING DETAILS ']");

    // locate "next" button in the order payment page
    final By shippingDetailsNextButtonLocator = By.xpath("//button[text()='NEXT']");

    // locate "payment method" part in the order payment page
    final By paymentMethodeLocator = By.xpath("//label[contains(text(),'2. PAYMENT METHOD') and contains(@class,'selected')]");

    // locate "payment methods option" in the payment method part
    final By PaymentMethodOptionsLocator = By.xpath("//div[@class='paymentMethods']");

    // locate "Master Credit" payment option
    final By masterCreditOptionLocator = By.xpath("//img[@alt='Master credit']");

    // locate "Mastercredit" card details
    final By masterCreditDetailsLocator = By.cssSelector("div.masterCreditSeccion");

    // locate pay now button in the payment method page
    final By payNowButtonLocator = By.id("pay_now_btn_MasterCredit");

    //locate the confirmation message after placed the order successfully
    final By thankYouMessageLocator = By.xpath("//span[text()='Thank you for buying with Advantage']");

    //locate safePay username field
    final By safePayUsername = By.xpath("//input[@name='safepay_username']");

    //locate safePay password field
    final By safePayPassword = By.xpath("//input[@name='safepay_password']");

    //locate save changes checkbox
    final By saveChanges = By.xpath("//input[@name='save_safepay']");

    //locate pay now button
    final By payNowBtn = By.xpath("//button[@id='pay_now_btn_SAFEPAY']");

    //locate thank you for buying text
    final By checkoutConfirmationMessage = By.xpath("//div[@id='orderPaymentSuccess']//h2");


    //-----------------methods-------------

    // method for verifying the product list page
    public boolean verifyingShippingDetailsPart() {
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(shippingDetailsPartLocator));
        return text.isDisplayed();
    }

    //Method for checking the "next" button in shipping details in  order payment page
    public void clickNextInShippingDetails() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(shippingDetailsNextButtonLocator));
        button.click();
    }

    //Method for checking navigation to the "payment method" in the order payment page
    public boolean verifyingPaymentMethodPart() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(paymentMethodeLocator));
        return element.isDisplayed();
    }

    //Method for ensuring the existence of "payment method options"
    public boolean verifyingPaymentMethodOptions() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(PaymentMethodOptionsLocator));
        return element.isDisplayed();
    }

    // Method to select "Master Credit" payment option
    public void selectMasterCreditOption() {
        WebElement masterCredit = wait.until(ExpectedConditions.elementToBeClickable(masterCreditOptionLocator));
        masterCredit.click();
    }

    // Method to check the visibility of mastercredit card details  visibility
    public boolean checkingMasterCreditCardDetails() {
        WebElement masterCreditDetails = wait.until(ExpectedConditions.elementToBeClickable(masterCreditDetailsLocator));
      return  masterCreditDetails.isDisplayed();
    }

    // Method to checking navigation to the confirmation part in payment method page
    public void ClickPayNowInPaymentMethod() {
        WebElement payNow = wait.until(ExpectedConditions.elementToBeClickable(payNowButtonLocator));
        payNow.click();
    }

    // Method to check the visibility of the confirmation message after placed the order successfully
    public boolean VerifyingConfirmationMessage() {
        WebElement confirmationMessage = wait.until(ExpectedConditions.elementToBeClickable(thankYouMessageLocator));
        return  confirmationMessage.isDisplayed();
    }

    public void SetSafePayUsername(String Username){
        driver.findElement(safePayUsername).sendKeys(Username);
    }

    public void SetSafePayPassword(String Password){
        driver.findElement(safePayPassword).sendKeys(Password);
    }

    public void ClickOnSaveChanges(){
        WebElement SaveChanges = wait.until(ExpectedConditions.elementToBeClickable(saveChanges));
        SaveChanges.click();
    }

    public void ClickOnPayNow(){
        driver.findElement(payNowBtn).click();
    }

    public String CheckoutConfirmationMessageVisibility(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutConfirmationMessage));
        return element.getText();
    }



}

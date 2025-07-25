package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import setup.Setup;

public class CompletePurchaseTest extends Setup {
    @Test
    public void CompletePurchase(){
        test.info("Test case started");
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"Home Page verified");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        LoginPage loginPage = homePage.ClickLoginOrSignUpButton();
        CreateAccountPage createAccountPage = loginPage.ClickOnRegisterBtn();
        try{
            Assert.assertEquals("CREATE ACCOUNT", createAccountPage.isRegistrationSuccessful());
            test.log(Status.PASS,"Navigated to Register Page Successfully");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        createAccountPage.fillRegistrationForm("mohamed5", "sherif8@gmail.com", "Test1234", "sherif", "hamada", "01234567891", "Egypt", "giza", "12 St", "giza", "12511");
        createAccountPage.agreeToTerms();
        createAccountPage.submitRegistration();
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"Register successful and navigated to home page");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        SpeakersPage speakersPage =homePage.ClickSpeakersButton();
        try{
            Assert.assertTrue(speakersPage.CheckSpeakersPageIsVisible());
            test.log(Status.PASS,"Successfully navigated to Speakers category and verified item visibility");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        speakersPage.clickOnManufactureFilter();
        speakersPage.clickOnHPManufactureFilter();
        speakersPage.clickOnColorFilter();
        speakersPage.clickOnBlackColorFilter();
        HPRoarMiniWirelessSpeakerDetailsPage hpRoarMiniWirelessSpeakerDetailsPage = speakersPage.clickOnHpSpeaker();
        hpRoarMiniWirelessSpeakerDetailsPage.putQuantity();
        hpRoarMiniWirelessSpeakerDetailsPage.clickOnColorFilter();
        hpRoarMiniWirelessSpeakerDetailsPage.addToCartButton();
        try{
            Assert.assertEquals("HP ROAR MINI WIRELESS SPEAKER", hpRoarMiniWirelessSpeakerDetailsPage.getProductNameFromPopUp());
            test.log(Status.PASS,"HP ROAR MINI WIRELESS SPEAKER added to cart and matches the name in pop-up");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        try{
            Assert.assertEquals("QTY: 5", hpRoarMiniWirelessSpeakerDetailsPage.getPopupQuantity());
            test.log(Status.PASS,"Quantity from pop-up matches the added quantity");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        hpRoarMiniWirelessSpeakerDetailsPage.ClickOnHomeFromBreadCrumb();
        //hpRoarMiniWirelessSpeakerDetailsPage.ClickCheckout();
        HeadPhonesPage headPhonesPage = homePage.ClickHeadphonesButton();
        BeatsStudio2OverEarMatteBlackHeadPhonesDetailsPage beatsStudio20DetailsPage = headPhonesPage.ClickOnProduct();
        beatsStudio20DetailsPage.setQuantity("10");
        beatsStudio20DetailsPage.clickAddToCart();
        try{
            Assert.assertEquals("BEATS STUDIO 2 OVER-EAR MAT...", beatsStudio20DetailsPage.getProductNameFromPopUp());
            test.log(Status.PASS,"'BEATS STUDIO 2 OVER-EAR MAT added to cart and matches the name in pop-up'");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        try{
            Assert.assertEquals("QTY: 10", beatsStudio20DetailsPage.getPopupQuantity());
            test.log(Status.PASS,"Quantity from pop-up matches the added quantity");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        CheckOutPage checkout = beatsStudio20DetailsPage.clickCheckout();
        checkout.clickNextInShippingDetails();





    }
}

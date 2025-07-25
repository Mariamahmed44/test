package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import setup.Setup;

public class TwoItemsPurchaseTest extends Setup {
    String username = "ALI";
    String password = "Pzx@2SvXmbXw";

    @Test
    public void verifyTwoItemsPurchased() {
        test.info("Test case started");
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"Home Page verified");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        LoginPage loginPage = homePage.ClickLoginOrSignUpButton();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"LoggedIn and Home Page Is Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
//        try{
//            Assert.assertTrue(homePage.CheckSpeakersButtonIsVisible());
//            test.log(Status.PASS,"Speakers Button Is Visible");
//        }catch (Exception e){
//            test.log(Status.FAIL,e.getCause() + e.getMessage());
//        }

        SpeakersPage speakersPage =homePage.clickSpeakersButton();

        try{
            Assert.assertTrue(speakersPage.CheckSpeakersPageIsVisible());
            test.log(Status.PASS,"Successfully navigated to Speakers category and verified item visibility");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        speakersPage.clickOnManufactureFilter();
        speakersPage.clickOnHPManufactureFilter();
        speakersPage.clickOnColorFilter();
        speakersPage.clickOnBlackColorFilter();





//        homePage.ScrollToPopularItemsArea();
//        HPEliteBookFolioDetailsPage hpEliteBookFolioDetailsPage = homePage.clickHPEliteBookFolioButton();
//        test.info("Click hp Button");
//        try{
//            Assert.assertTrue(hpEliteBookFolioDetailsPage.VerifyEliteBookPDP());
//            test.log(Status.PASS,"Products List Is Visible");
//        }catch (Exception e){
//            test.log(Status.FAIL,e.getCause() + e.getMessage());
//        }


    }



}



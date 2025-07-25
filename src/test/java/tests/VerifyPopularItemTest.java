package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import setup.Setup;

public class VerifyPopularItemTest extends Setup {
    @Test
    public void VerifyPopularItem(){
        test.info("Test case started");
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"Home Page verified");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        HPEliteBookFolioDetailsPage HPEliteBookPage=homePage.clickHPEliteBookFolioButton();
        try{
            Assert.assertTrue(HPEliteBookPage.VerifyEliteBookPDP());
            test.log(Status.PASS,"Successful product detail page redirection");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        try{
            Assert.assertEquals("HP ELITEBOOK FOLIO", HPEliteBookPage.VerifyEliteBookName());

            test.log(Status.PASS,"Correct product details page of HP ELITEBOOK FOLIO");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        test.info("Test case Ended");



    }
}

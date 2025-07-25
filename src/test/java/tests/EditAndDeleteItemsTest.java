package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import setup.Setup;

public class EditAndDeleteItemsTest extends Setup {
    public String username = "ALI";
    public String password = "Pzx@2SvXmbXw";

    @Test
    public void verifyEditAndDeleteOptionsForProductItems() {
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

        try{
            Assert.assertEquals(username, homePage.UserLoggedInUser());
            test.log(Status.PASS,"User Matches Logged In User Name");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        homePage.ScrollToExploreMoreArea();
        try{
            Assert.assertTrue(homePage.CheckExploreNowIsVisible());
            test.log(Status.PASS,"Explore Now Area Is Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        TabletsPage tabletsPage=homePage.ClickExploreNowButton();
        try{
            Assert.assertTrue(tabletsPage.CheckTabletsPageIsVisible());
            test.log(Status.PASS,"Navigated To Explore Now Page(Tablets Page) And Items Are Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        HPElitePad1000G2TabletDetailsPage hpElitePadDetailsPage = tabletsPage.ClickOnFirstProduct();
        try{
            Assert.assertEquals("HP ELITEPAD 1000 G2 TABLET",hpElitePadDetailsPage.VerifyElitePadG2Name());
            test.log(Status.PASS,"Navigated To First Product (HP ELITEPAD 1000 G2 TABLET) Successfully");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        try{
            Assert.assertTrue(hpElitePadDetailsPage.VerifyElitePadG2PDP());
            test.log(Status.PASS,"Product Details Are Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        hpElitePadDetailsPage.ClickAddToCart();
        try{
            Assert.assertEquals("HP ELITEPAD 1000 G2 TABLET", hpElitePadDetailsPage.VerifyElitePadG2NameInPopUp());
            test.log(Status.PASS,"HP ELITEPAD 1000 G2 TABLET added to cart and matches the name in pop-up");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        hpElitePadDetailsPage.ElitePadG2ClickTabletBreadCrumb();
        try{
            Assert.assertTrue(tabletsPage.CheckTabletsPageIsVisible());
            test.log(Status.PASS,"Navigates Back To Tablets Page Through Bread Crumb Succeeded");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        HPElite_x2_1011G1TabletDetailsPage hpEliteTabletDetailsPage =tabletsPage.ClickOnSecondProduct();
        try{
            Assert.assertEquals("HP ELITE X2 1011 G1 TABLET",hpEliteTabletDetailsPage.VerifyEliteTabletG1Name());
            test.log(Status.PASS,"Navigated To Second Product (HP ELITE X2 1011 G1 TABLET) Successfully");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        try{
            Assert.assertTrue(hpEliteTabletDetailsPage.VerifyEliteTabletG1PDP());
            test.log(Status.PASS,"Product Details Are Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        hpEliteTabletDetailsPage.EliteTabletG1ClickAddToCart();
        try{
            Assert.assertEquals("HP ELITE X2 1011 G1 TABLET", hpEliteTabletDetailsPage.VerifyEliteTabletG1NameInPopUp());
            test.log(Status.PASS,"HP ELITE X2 1011 G1 TABLET added to cart and matches the name in pop-up");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        hpEliteTabletDetailsPage.ClickOnXIconToDeleteHpEliteX2inPopUp();
        try{
            Assert.assertTrue(hpEliteTabletDetailsPage.VerifyEliteTabletG1Disappears());
            test.log(Status.PASS,"Product Deleted Successfully ");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        CartPage cartPage = hpEliteTabletDetailsPage.ClickOnCartIcon();
        try{
            Assert.assertTrue(cartPage.CheckCartPageIsVisible());
            test.log(Status.PASS,"Cart Page Is Visible And Added Products Appear Successfully");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        cartPage.ClickEdit();
        try{
            Assert.assertTrue(hpElitePadDetailsPage.VerifyElitePadG2PDP());
            test.log(Status.PASS,"Navigating Back To Product Details Page To Edit On Product succeeded");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        hpElitePadDetailsPage.ClickOnGreyColorToEdit();
        hpElitePadDetailsPage.ClickAddToCart();
        try{
            Assert.assertTrue(cartPage.CheckCartPageIsVisible());
            test.log(Status.PASS,"Cart is displayed correctly, reflecting the added product");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        test.info("Test case Ended");
    }



}



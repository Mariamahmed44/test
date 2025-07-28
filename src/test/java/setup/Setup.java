package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Setup {
    protected WebDriver driver;
    protected HomePage homePage;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter htmlReporter;
    protected ExtentTest test;



    @BeforeSuite
    public void setUpExtentReport() throws IOException {
        extent = new ExtentReports();
        htmlReporter = new ExtentSparkReporter("src/test/java/utils/reports/UI_test_report.html");
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setCss(".nav-logo .logo { background-image: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; }");
    }

    @BeforeClass
    public void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        driver = new ChromeDriver(chromeOptions);
        goHome();

    }

    public void goHome() {
        driver.get("https://advantageonlineshopping.com/#/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @BeforeMethod
    public void startTest(Method method){
        test = extent.createTest(method.getName());
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void tearDownExtentReport() {
        extent.flush();
    }


}

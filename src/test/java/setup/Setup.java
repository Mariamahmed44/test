package setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import pages.HomePage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

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
        htmlReporter.config().setCss(
                ".nav-logo .logo { background-image: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; }" +
                ".report-logo > img { content: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; height: 50px; }" +
                ".navbar, .vheader { background-color: #2900ca !important; }" +
                ".nav-left a, .nav-right a, .nav-left i, .nav-right i { color: white !important; }"
        );
    }

    public void goHome() {
        driver.get("https://advantageonlineshopping.com/#/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @BeforeClass
    public void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        
        // For CI environments add these:
        // chromeOptions.addArguments("--headless=new");
        // chromeOptions.addArguments("--no-sandbox");
        // chromeOptions.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(chromeOptions);
        goHome();
    }

    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownExtentReport() {
        extent.flush();
    }
}

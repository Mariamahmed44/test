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
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class Setup {
    protected WebDriver driver;
    protected HomePage homePage;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter htmlReporter;
    protected ExtentTest test;
    
    @BeforeSuite
    public void setUpExtentReport() throws IOException {
        // Create reports directory if it doesn't exist
        File reportsDir = new File("src/test/java/utils/reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        
        // Also create target directory for CI/CD compatibility
        File targetReportsDir = new File("target/extent-reports");
        if (!targetReportsDir.exists()) {
            targetReportsDir.mkdirs();
        }
        
        extent = new ExtentReports();
        
        // Create reports in both locations for compatibility
        htmlReporter = new ExtentSparkReporter("src/test/java/utils/reports/UI_test_report.html");
        ExtentSparkReporter targetReporter = new ExtentSparkReporter("target/extent-reports/UI_test_report.html");
        
        extent.attachReporter(htmlReporter);
        extent.attachReporter(targetReporter);
        
        // Set system information
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Environment", System.getProperty("test.env", "CI"));
        
        // Configure theme and styling
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Konecta Test Automation Report");
        htmlReporter.config().setReportName("UI Test Execution Report");
        
        // Custom CSS styling
        htmlReporter.config().setCss(
            ".nav-logo .logo { background-image: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; }" +
            ".report-logo > img { content: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; height: 50px; }" +
            ".navbar, .vheader { background-color: #2900ca !important; }" +
            ".nav-left a, .nav-right a, .nav-left i, .nav-right i { color: white !important; }"
        );
        
        // Apply same configuration to target reporter
        targetReporter.config().setTheme(Theme.STANDARD);
        targetReporter.config().setDocumentTitle("Konecta Test Automation Report");
        targetReporter.config().setReportName("UI Test Execution Report");
        targetReporter.config().setCss(
            ".nav-logo .logo { background-image: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; }" +
            ".report-logo > img { content: url('https://ik.imagekit.io/sfkyshz6p/Konecta%20Logo-Yellow.png') !important; height: 50px; }" +
            ".navbar, .vheader { background-color: #2900ca !important; }" +
            ".nav-left a, .nav-right a, .nav-left i, .nav-right i { color: white !important; }"
        );
    }
    
    @BeforeClass
    public void setup() {
        try {
            // Setup WebDriverManager for automatic driver management
            WebDriverManager.chromedriver().setup();
            
            // Configure Chrome options for CI/CD environment
            ChromeOptions chromeOptions = new ChromeOptions();
            
            // Essential options for CI environments
            chromeOptions.addArguments("--headless"); // Run in headless mode
            chromeOptions.addArguments("--no-sandbox"); // Required for CI environments
            chromeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            chromeOptions.addArguments("--disable-gpu"); // Disable GPU acceleration
            chromeOptions.addArguments("--window-size=1920,1080"); // Set window size
            chromeOptions.addArguments("--remote-debugging-port=9222"); // Enable remote debugging
            
            // Certificate and security options
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--ignore-ssl-errors=yes");
            chromeOptions.addArguments("--ignore-certificate-errors-spki-list");
            chromeOptions.addArguments("--insecure-handle-for-testing");
            chromeOptions.addArguments("--disable-web-security");
            chromeOptions.addArguments("--allow-running-insecure-content");
            
            // Performance and stability options
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-plugins");
            chromeOptions.addArguments("--disable-images"); // Faster loading
            chromeOptions.addArguments("--disable-javascript"); // Only if your tests don't require JS
            chromeOptions.addArguments("--disable-default-apps");
            
            // Memory and crash handling
            chromeOptions.addArguments("--max_old_space_size=4096");
            chromeOptions.addArguments("--disable-logging");
            chromeOptions.addArguments("--disable-background-timer-throttling");
            chromeOptions.addArguments("--disable-renderer-backgrounding");
            chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
            
            // Create WebDriver instance
            driver = new ChromeDriver(chromeOptions);
            
            // Set timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            
            // Navigate to home page
            goHome();
            
        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }
    
    public void goHome() {
        try {
            System.out.println("Navigating to: https://advantageonlineshopping.com/#/");
            driver.get("https://advantageonlineshopping.com/#/");
            
            // Only maximize if not in headless mode
            String headless = System.getProperty("headless", "true");
            if (!"true".equals(headless)) {
                driver.manage().window().maximize();
            }
            
            // Wait for page to load
            Thread.sleep(3000);
            
            homePage = new HomePage(driver);
            System.out.println("Successfully navigated to home page");
            
        } catch (Exception e) {
            System.err.println("Failed to navigate to home page: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Navigation failed", e);
        }
    }
    
    @BeforeMethod
    public void startTest(Method method) {
        try {
            test = extent.createTest(method.getName());
            System.out.println("Starting test: " + method.getName());
        } catch (Exception e) {
            System.err.println("Failed to start test: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @AfterMethod
    public void afterTest() {
        // Add any cleanup needed after each test
        if (test != null) {
            System.out.println("Completed test: " + test.getModel().getName());
        }
    }
    
    @AfterClass
    public void tearDown() {
        try {
            if (driver != null) {
                System.out.println("Closing WebDriver");
                driver.quit();
            }
        } catch (Exception e) {
            System.err.println("Error during WebDriver cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @AfterSuite
    public void tearDownExtentReport() {
        try {
            if (extent != null) {
                extent.flush();
                System.out.println("ExtentReports flushed successfully");
                System.out.println("Reports generated at:");
                System.out.println("- src/test/java/utils/reports/UI_test_report.html");
                System.out.println("- target/extent-reports/UI_test_report.html");
            }
        } catch (Exception e) {
            System.err.println("Error during ExtentReports cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

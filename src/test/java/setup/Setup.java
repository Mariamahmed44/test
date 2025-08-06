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
import java.util.UUID;

public class Setup {
    protected WebDriver driver;
    protected HomePage homePage;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter htmlReporter;
    protected ExtentTest test;
    private Path tempUserDataDir;

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
    public void setup() throws IOException {
        // Create unique temporary directory for Chrome user data
        tempUserDataDir = Files.createTempDirectory("chrome_user_data_" + UUID.randomUUID().toString());
        
        ChromeOptions chromeOptions = new ChromeOptions();
        
        // Essential options for all environments
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        
        // Unique user data directory to prevent conflicts
        chromeOptions.addArguments("--user-data-dir=" + tempUserDataDir.toString());
        
        // Use random debugging port to avoid conflicts
        chromeOptions.addArguments("--remote-debugging-port=0");
        
        // Check if running in CI environment
        String isCI = System.getenv("CI");
        String githubActions = System.getenv("GITHUB_ACTIONS");
        String runningInContainer = System.getenv("RUNNING_IN_CONTAINER");
        
        if ("true".equals(isCI) || "true".equals(githubActions) || "true".equals(runningInContainer)) {
            // CI/CD specific options
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-background-timer-throttling");
            chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
            chromeOptions.addArguments("--disable-renderer-backgrounding");
            chromeOptions.addArguments("--window-size=1920,1080");
        }
        
        // Additional options for stability
        chromeOptions.addArguments("--disable-features=TranslateUI");
        chromeOptions.addArguments("--disable-ipc-flooding-protection");
        chromeOptions.addArguments("--disable-background-networking");
        chromeOptions.addArguments("--disable-default-apps");
        chromeOptions.addArguments("--disable-sync");
        
        try {
            driver = new ChromeDriver(chromeOptions);
            goHome();
        } catch (Exception e) {
            System.err.println("Failed to initialize ChromeDriver: " + e.getMessage());
            throw e;
        }
    }

    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterClass
    public void tearDown() {
        // Close WebDriver
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error closing WebDriver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
        
        // Clean up temporary user data directory
        if (tempUserDataDir != null) {
            try {
                deleteDirectoryRecursively(tempUserDataDir);
            } catch (IOException e) {
                System.err.println("Failed to clean up temp directory: " + e.getMessage());
            }
        }
    }

    @AfterSuite
    public void tearDownExtentReport() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    /**
     * Recursively delete a directory and its contents
     */
    private void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        System.err.println("Failed to delete: " + p + " - " + e.getMessage());
                    }
                });
        }
    }
    
    /**
     * Method to check if tests are running in parallel
     */
    private boolean isRunningInParallel() {
        // Check TestNG configuration or system properties
        String parallel = System.getProperty("testng.parallel");
        return parallel != null && !parallel.equals("none");
    }
    
    /**
     * Alternative setup method for when you need multiple driver instances
     * Call this instead of setup() if you're running parallel tests
     */
    @BeforeClass
    public void setupForParallel() throws IOException {
        // Create unique identifier using thread ID and timestamp
        String uniqueId = Thread.currentThread().getId() + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
        tempUserDataDir = Files.createTempDirectory("chrome_parallel_" + uniqueId);
        
        ChromeOptions chromeOptions = new ChromeOptions();
        
        // All the same options as setup() method
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--user-data-dir=" + tempUserDataDir.toString());
        chromeOptions.addArguments("--remote-debugging-port=0");
        
        // Force headless for parallel execution to avoid display conflicts
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--window-size=1920,1080");
        
        // Additional parallel-specific options
        chromeOptions.addArguments("--disable-background-timer-throttling");
        chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
        chromeOptions.addArguments("--disable-renderer-backgrounding");
        chromeOptions.addArguments("--disable-features=TranslateUI");
        chromeOptions.addArguments("--disable-ipc-flooding-protection");
        
        try {
            driver = new ChromeDriver(chromeOptions);
            goHome();
        } catch (Exception e) {
            System.err.println("Failed to initialize ChromeDriver for parallel execution: " + e.getMessage());
            throw e;
        }
    }
}

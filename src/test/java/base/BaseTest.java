package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import config.ConfigManager;
import driver.DriverFactory;
import driver.DriverManager;
import logging.LoggerUtils;

public class BaseTest {
	
	protected final Logger log = LoggerUtils.getLogger(getClass());
	
	@BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext context) {

        String browser = context.getCurrentXmlTest().getParameter("browser");
        String env = context.getCurrentXmlTest().getParameter("env");

        if (browser == null || browser.isBlank()) {
            browser = System.getProperty("browser", "chrome");
        }

        if (env == null || env.isBlank()) {
            env = System.getProperty("env", "qa");
        }
        

        log.info("===== Test Setup Started =====");
        log.info("Browser: {}", browser);
        log.info("Environment: {}", env);
        
        ThreadContext.put("browser", browser);
        ThreadContext.put("environment", env);
        ThreadContext.put("className", getClass().getSimpleName());
        
        ConfigManager.loadProperties(env);
        log.info("Config loaded for env: {}", env);

        DriverFactory.initDriver(browser);
        log.info("Driver initialized for browser: {}", browser);

        String baseUrl = ConfigManager.getProperty("baseUrl");
        DriverManager.getDriver().get(baseUrl);

        log.info("Navigated to URL: {}", baseUrl);
        log.info("===== Test Setup Completed =====");
    }
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

	    String status;

	    switch (result.getStatus()) {
	        case ITestResult.SUCCESS:
	            status = "PASSED";
	            break;
	        case ITestResult.FAILURE:
	            status = "FAILED";
	            break;
	        case ITestResult.SKIP:
	            status = "SKIPPED";
	            break;
	        default:
	            status = "UNKNOWN";
	            break;
	    }

	    ThreadContext.put("testName", result.getMethod().getMethodName());
	    ThreadContext.put("testStatus", status);

	    log.info("===== Test Teardown Started =====");
	    log.info("Test Name: {}", result.getMethod().getMethodName());
	    log.info("Test Status: {}", status);

	    if (result.getThrowable() != null) {
	        log.error("Test failed", result.getThrowable());
	    }

	    DriverFactory.quitDriver();

	    log.info("Driver closed successfully");
	    log.info("===== Test Teardown Completed =====");

	    ThreadContext.clearAll();
	}
	
	//	---- This detDriver is only helping us to use driver like 
	// --- getDriver().findElement through out the test on place of using the 
	// --- DriverManager.getDrriver().findElement
	//	public WebDriver getDriver() {
	//        return DriverFactory.getDriver();
	//    }
	
}

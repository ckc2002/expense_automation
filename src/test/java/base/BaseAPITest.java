package base;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import config.ConfigManager;
import io.restassured.RestAssured;
import logging.LoggerUtils;

public class BaseAPITest {
	
	protected final Logger log = LoggerUtils.getLogger(getClass());

	@BeforeMethod(alwaysRun = true)
    public void setUpApiDefaults(ITestContext context) {
   
        
        String env = context.getCurrentXmlTest().getParameter("env");
        
        if (env == null || env.isBlank()) {
            env = System.getProperty("env", "qa");
        }
        
        ConfigManager.loadProperties(env);
        
        log.info("Config loaded for env: {}", env);
        log.info("API test configuration initialized");
        
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}

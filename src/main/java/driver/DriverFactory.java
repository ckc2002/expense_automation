package driver;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import config.ConfigManager;

public class DriverFactory {
	
	public static void initDriver(String browser) {
        String browserToUse = (browser == null || browser.isBlank())
                ? ConfigManager.getProperty("browser", "chrome")
                : browser;

        WebDriver driver = BrowserFactory.createDriver(browserToUse);

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigManager.getIntProperty("implicitWait", 5))
        );

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigManager.getIntProperty("pageLoadTimeout", 30))
        );

        String runMode = ConfigManager.getProperty("runMode", "local");
        if (!"remote".equalsIgnoreCase(runMode)) {
            driver.manage().window().maximize();
        }

        DriverManager.setDriver(driver);
    }
	 
	 public static void quitDriver() {
	        WebDriver driver = DriverManager.getDriver();
	        if (driver != null) {
	            driver.quit();
	            DriverManager.unload();
	        }
	  }
	 
}

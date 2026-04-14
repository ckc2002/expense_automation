package driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	public static WebDriver createDriver(String browserName) {
        String runMode = ConfigManager.getProperty("runMode", "local");
     // Smart default
        boolean headless = ConfigManager.getBooleanProperty(
                "headless",
                "remote".equalsIgnoreCase(runMode) // default true for remote
        );

        if ("remote".equalsIgnoreCase(runMode)) {
            return createRemoteDriver(browserName, headless);
        } else {
            return createLocalDriver(browserName, headless);
        }
    }
	
	private static WebDriver createLocalDriver(String browserName, boolean headless) {
        switch (browserName.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                return new EdgeDriver(edgeOptions);

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver(chromeOptions);
        }
    }
	
	private static WebDriver createRemoteDriver(String browserName, boolean headless) {
		String remoteUrl = ConfigManager.getProperty("remoteUrl");

	    try {
	        switch (browserName.toLowerCase()) {
	
	            case "firefox":
	                FirefoxOptions firefoxOptions = new FirefoxOptions();
	                if (headless) firefoxOptions.addArguments("-headless");
	                return new RemoteWebDriver(new URL(remoteUrl), firefoxOptions);
	
	            case "edge":
	                EdgeOptions edgeOptions = new EdgeOptions();
	                if (headless) edgeOptions.addArguments("--headless=new");
	                return new RemoteWebDriver(new URL(remoteUrl), edgeOptions);
	
	            case "chrome":
	            default:
	                ChromeOptions chromeOptions = new ChromeOptions();
	                if (headless) chromeOptions.addArguments("--headless=new");
	
	                chromeOptions.addArguments("--no-sandbox");
	                chromeOptions.addArguments("--disable-dev-shm-usage");
	                chromeOptions.addArguments("--start-maximized");
	
	                return new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
	        }
	
	    } catch (MalformedURLException e) {
	        throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
	    }
	}	
}

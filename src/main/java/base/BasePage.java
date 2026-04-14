package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import driver.DriverFactory;
import driver.DriverManager;
import utils.WaitUtils;

public class BasePage {

	protected final WebDriver driver;
	protected final WaitUtils wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
    
    protected void click(WebElement element) {
    	wait.waitForClick(element).click();;
    }
    
    protected void type(WebElement element,String text) {
    	 wait.waitForVisibility(element);
    	 element.clear();
    	 element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
    	return wait.waitForVisibility(element).getText();
    }
    
}

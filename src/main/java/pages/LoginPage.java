package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;
import utils.WaitUtils;

public class LoginPage extends BasePage {

	private WaitUtils wait;
	public LoginPage() {
        super();
        this.wait = new WaitUtils(driver);
    }
	
	@FindBy(name = "identifier")
    private WebElement usernameInput;
	
	@FindBy(name = "password")
    private WebElement passwordInput;
	
	@FindBy(xpath = "//button[contains(@class,'cl-formButtonPrimary cl-button') and contains(normalize-space(),'Continue')]")
    private WebElement loginButton;
	
	public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        click(loginButton);
        return this;
    }
	
	public LoginPage enterPassword(String password) {
         type(passwordInput, password);
       return this;
    }
	
	public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage();
    }
	
	public DashboardPage loginAs(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}

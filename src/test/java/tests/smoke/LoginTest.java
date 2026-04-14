package tests.smoke;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.DashboardPage;
import pages.LoginPage;
import base.BaseTest;
import dataproviders.UserDataProvider;

@Epic("Demo Application")
@Feature("Authentication")
public class LoginTest extends BaseTest {

	@Test(dataProvider = "loginUsers", dataProviderClass = UserDataProvider.class, groups={"smoke"})
	@Story("Valid Login")
	@Owner("Peter")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Verify that a user can log in successfully and land on the dashboard")
	public void verifyLogin(String username, String password, String name) {
		
		 DashboardPage dashboardPage = performLogin(username, password);
		
		 boolean isDashboardDisplayed = dashboardPage.verifyDashboardDisplayed(name);
		 
		 log.info("Dashboard verification result for user '{}': {}", username, isDashboardDisplayed);

	      Assert.assertTrue(
	           isDashboardDisplayed,
	           "Login failed or dashboard was not displayed correctly for user: " + username
	      );

	      log.info("Login test passed for username: {}", username);
	}
	
	@Step("Login with username: {username}")
	private DashboardPage performLogin(String name, String password) {
		LoginPage loginPage = new LoginPage();
		return loginPage.loginAs(name, password);
	}
}

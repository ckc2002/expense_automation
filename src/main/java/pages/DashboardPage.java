package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;
import utils.WaitUtils;

public class DashboardPage extends BasePage {

    private final WaitUtils wait;

    public DashboardPage() {
        super();
        this.wait = new WaitUtils(driver);
    }

    @FindBy(xpath = "//h2[contains(@class,'font-bold') and contains(normalize-space(),'Hi')]")
    private WebElement dashboardText;

    @FindBy(xpath = "//h2[contains(@class,'text-primary') and contains(normalize-space(),'Dashboard')]")
    private WebElement dashboardLink;

    public boolean verifyDashboardDisplayed(String expectedName) {
        String actualGreetingText = wait.waitForVisibility(dashboardText).getText().trim();
        boolean isDashboardLinkVisible = wait.waitForVisibility(dashboardLink).isDisplayed();

        return expectedName != null
                && actualGreetingText.toLowerCase().contains(expectedName.toLowerCase())
                && isDashboardLinkVisible;
    }
}

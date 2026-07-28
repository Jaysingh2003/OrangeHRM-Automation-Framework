package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utilities.WaitUtil;
import java.util.List;

public class DashboardPage {
    private final WebDriver driver;

    @FindBy(css = ".oxd-topbar-header-breadcrumb h6")
    private WebElement pageHeader;

    @FindBy(css = ".oxd-userdropdown-tab")
    private WebElement userDropdown;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement userName;

    @FindBy(xpath = "//ul[@class='oxd-userdropdown-menu']//a[text()='Logout']")
    private WebElement logoutLink;

    @FindBy(css = ".oxd-grid-item--gutters")
    private List<WebElement> quickLaunchItems;

    @FindBy(css = ".oxd-main-menu-item")
    private List<WebElement> sideMenuItems;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isDashboardDisplayed() {
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-topbar-header-breadcrumb h6"));
        return pageHeader.getText().equalsIgnoreCase("Dashboard");
    }

    public String getLoggedInUser() {
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-userdropdown-name"));
        return userName.getText();
    }

    public LoginPage logout() {
        userDropdown.click();
        WaitUtil.waitForClickable(driver, By.xpath("//ul[@class='oxd-userdropdown-menu']//a[text()='Logout']"));
        logoutLink.click();
        return new LoginPage(driver);
    }

    public int getQuickLaunchCount() {
        return quickLaunchItems.size();
    }

    public int getSideMenuCount() {
        return sideMenuItems.size();
    }

    public void navigateTo(String menuItem) {
        sideMenuItems.stream()
                .filter(e -> e.getText().trim().equalsIgnoreCase(menuItem))
                .findFirst()
                .ifPresent(WebElement::click);
    }
}

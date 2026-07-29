package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        dashboardPage = new LoginPage(getDriver()).login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @Test(groups = "smoke", priority = 1)
    public void testDashboardOpens() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard not displayed");
    }

    @Test(groups = "smoke", priority = 2)
    public void testUserProfileVisible() {
        Assert.assertFalse(dashboardPage.getLoggedInUser().isEmpty(), "User name not visible");
    }

    @Test(groups = "regression", priority = 3)
    public void testQuickLaunchVisible() {
        Assert.assertTrue(dashboardPage.getQuickLaunchCount() > 0, "Quick launch items not found");
    }

    @Test(groups = "regression", priority = 4)
    public void testSideMenuVisible() {
        Assert.assertTrue(dashboardPage.getSideMenuCount() > 0, "Side menu items not found");
    }

    @Test(groups = "regression", priority = 5)
    public void testNavigationToAdmin() {
        dashboardPage.navigateTo("Admin");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("admin"), "Admin page not loaded");
    }
}

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class AdminTest extends BaseTest {

    private AdminPage adminPage;
    private static final String TEST_USERNAME = "testuser_auto";

    @BeforeMethod(alwaysRun = true)
    public void navigateToAdmin() {
        DashboardPage dashboard = new LoginPage(getDriver()).login(ConfigReader.getUsername(), ConfigReader.getPassword());
        dashboard.navigateTo("Admin");
        adminPage = new AdminPage(getDriver());
    }

    @Test(groups = "regression", priority = 1, description = "Add a new system user")
    public void testAddUser() {
        adminPage.clickAdd();
        adminPage.fillUserForm("ESS", "Peter Mac Anderson", "Enabled", TEST_USERNAME, "Admin@1234");
        adminPage.saveForm();
        adminPage.searchByUsername(TEST_USERNAME);
        Assert.assertTrue(adminPage.isUserPresent(TEST_USERNAME), "User not found after adding");
    }

    @Test(groups = "regression", priority = 2, dependsOnMethods = "testAddUser", description = "Search user by username")
    public void testSearchByUsername() {
        adminPage.searchByUsername(TEST_USERNAME);
        Assert.assertTrue(adminPage.getTableRowCount() >= 1, "No results found for username search");
    }

    @Test(groups = "regression", priority = 3, dependsOnMethods = "testSearchByUsername", description = "Delete user")
    public void testDeleteUser() {
        adminPage.searchByUsername(TEST_USERNAME);
        adminPage.deleteUser(TEST_USERNAME);
        adminPage.searchByUsername(TEST_USERNAME);
        Assert.assertFalse(adminPage.isUserPresent(TEST_USERNAME), "User still present after deletion");
    }

    @Test(groups = "regression", priority = 4, description = "Reset button clears search fields")
    public void testResetButton() {
        adminPage.searchByUsername("someuser");
        adminPage.clickReset();
        String url = getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("admin"), "Not on admin page after reset");
    }

    @Test(groups = "regression", priority = 5, description = "Table data is displayed")
    public void testTableData() {
        Assert.assertTrue(adminPage.getTableRowCount() > 0, "Admin table is empty");
    }
}

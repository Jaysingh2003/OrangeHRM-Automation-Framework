package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utilities.WaitUtil;
import java.util.List;

public class AdminPage {
    private final WebDriver driver;

    @FindBy(css = "input[placeholder='Type for hints...']")
    private WebElement employeeNameInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = "button[type='reset']")
    private WebElement resetButton;

    @FindBy(css = ".oxd-button--secondary.orangehrm-left-space")
    private WebElement addButton;

    @FindBy(css = ".oxd-table-body .oxd-table-row")
    private List<WebElement> tableRows;

    @FindBy(css = ".oxd-table-footer .oxd-pagination")
    private WebElement pagination;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAdd() {
        WaitUtil.waitForClickable(driver, By.cssSelector(".oxd-button--secondary.orangehrm-left-space"));
        addButton.click();
    }

    public void fillUserForm(String userRole, String employeeName, String status, String username, String password) {
        // User Role dropdown
        List<WebElement> dropdowns = driver.findElements(By.cssSelector(".oxd-select-text-input"));
        dropdowns.get(0).click();
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-select-dropdown"));
        driver.findElements(By.cssSelector(".oxd-select-option span"))
                .stream().filter(e -> e.getText().equals(userRole)).findFirst().ifPresent(WebElement::click);

        // Employee Name
        employeeNameInput.sendKeys(employeeName);
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-autocomplete-dropdown"));
        driver.findElement(By.cssSelector(".oxd-autocomplete-option span")).click();

        // Status dropdown
        dropdowns.get(1).click();
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-select-dropdown"));
        driver.findElements(By.cssSelector(".oxd-select-option span"))
                .stream().filter(e -> e.getText().equals(status)).findFirst().ifPresent(WebElement::click);

        // Username & Password
        List<WebElement> inputs = driver.findElements(By.cssSelector(".oxd-input:not([type='hidden'])"));
        inputs.get(1).sendKeys(username);
        inputs.get(2).sendKeys(password);
        inputs.get(3).sendKeys(password);
    }

    public void saveForm() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public void searchByUsername(String username) {
        WaitUtil.waitForVisible(driver, By.cssSelector("input[placeholder='Username']"));
        driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys(username);
        searchButton.click();
    }

    public void clickReset() {
        resetButton.click();
    }

    public int getTableRowCount() {
        WaitUtil.waitForVisible(driver, By.cssSelector(".oxd-table-body"));
        return tableRows.size();
    }

    public void deleteUser(String username) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"));
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                row.findElement(By.cssSelector(".oxd-icon-button.oxd-table-cell-action-space")).click();
                WaitUtil.waitForClickable(driver, By.cssSelector(".oxd-button--label-danger"));
                driver.findElement(By.cssSelector(".oxd-button--label-danger")).click();
                break;
            }
        }
    }

    public boolean isUserPresent(String username) {
        return driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"))
                .stream().anyMatch(r -> r.getText().contains(username));
    }
}

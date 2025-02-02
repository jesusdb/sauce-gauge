package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage extends BasePage {
    private By usernameInputLocator = By.id("user-name");
    private By passwordInputLocator = By.id("password");
    private By submitButtonLocator = By.id("login-button");
    private By errorMessageLocator = By.className("error-message-container");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void visit() {
        super.visit("https://www.saucedemo.com/");
    }

    public void enterUsername(String username) {
        driver.findElement(usernameInputLocator).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInputLocator).sendKeys(password);
    }

    public void clickSubmitButton() {
        driver.findElement(submitButtonLocator).click();
    }

    public InventoryPage logIn(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmitButton();

        return new InventoryPage(driver);
    }

    public String getErrorMessage() {
        WebElement errorMessageElement = waitForElementToBeVisible(errorMessageLocator);
        return errorMessageElement.getText();
    }
}

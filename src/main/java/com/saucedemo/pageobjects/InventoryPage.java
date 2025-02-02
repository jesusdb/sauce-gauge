package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;

public class InventoryPage extends BasePage {
    private By burgerMenuButtonLocator = By.id("react-burger-menu-btn");
    private By logOutButtonLocator = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoutButtonDisplayed() {
        WebElement burgerMenuButton = driver.findElement(burgerMenuButtonLocator);
        burgerMenuButton.click();

        try {
            waitForElementToBeVisible(logOutButtonLocator);
        } catch (TimeoutException e) {
            return false;
        }

        return true; // isDisplayed(logOutButtonLocator);
    }

    public void load() {
        waitForElementToBeVisible(burgerMenuButtonLocator);
    }
}

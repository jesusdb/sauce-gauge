package com.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {
    private By burgerMenuButtonLocator = By.id("react-burger-menu-btn");
    private By logOutButtonLocator = By.id("logout_sidebar_link");
    private By productSortSelectLocator = By.className("product_sort_container");
    private By inventoryItemsLocator = By.className("inventory_item");
    private By inventoryItemNamesLocator = By.className("inventory_item_name");
    private By inventoryItemPricesLocator = By.className("inventory_item_price");

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

    public void sortProductsByValue(String value) {
        getProductSortSelect().selectByValue(value);
    }

    public void orderProductsDescending() {
        sortProductsByValue("za");
    }

    private Select getProductSortSelect() {
        WebElement productSortSelect = driver.findElement(productSortSelectLocator);
        return new Select(productSortSelect);
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItemsLocator);
    }

    public List<WebElement> getInventoryItemNames() {
        return driver.findElements(inventoryItemNamesLocator);
    }

    public List<WebElement> getInventoryItemPrices() {
        return driver.findElements(inventoryItemPricesLocator);
    }
}

package com.saucedemo.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.saucedemo.pageobjects.InventoryPage;
import com.saucedemo.pageobjects.LoginPage;
import com.saucedemo.pageobjects.ListUtils;

import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import java.util.List;

public class ProductsFilteringAndCartManagementTests {
    private WebDriver driver;
    private Logger logger = Logger.getLogger(getClass().getName());

    @BeforeScenario
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        logger.log(Level.INFO, "Starting test with browser: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                this.driver = new ChromeDriver();
                break;
            case "firefox":
                this.driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Configuration for " + browser + " is missing, so running tests in Chrome by default");
                this.driver = new ChromeDriver();
                break;
        }
    }

    @Step("Verify products are sorted correctly according to the following table: <table>")
    public void testProductsSorting(Table table) {
        logger.info("Starting testSortProducts");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        InventoryPage inventoryPage = loginPage.logIn("standard_user", "secret_sauce");
        inventoryPage.load();

        logger.info("Verify the sort functionality");
        assertEachCriteriaFromTableIsSortedCorrectly(table, inventoryPage);
    }

    @Step("Add products from the following list to cart: <table>")
    public void testAddProductToCart(Table table) {
        logger.info("Starting testAddProductToCart");
        InventoryPage inventoryPage = logIn();

        logger.info("Adding products from table to cart");
        addProductsFromTableToCart(table, inventoryPage);

        assertThat(inventoryPage.getShoppingCartBadgeCount()).isEqualTo(3);
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
        logger.info("Browser is closed");
    }

    private void assertEachCriteriaFromTableIsSortedCorrectly(Table table, InventoryPage inventoryPage) {
        for (TableRow row : table.getTableRows()) {
            String sortOption = row.getCell("Sort Option");
            String sortCriteria = row.getCell("Sort Criteria");
            
            logger.info("Sorting product by " + sortOption);
            inventoryPage.sortProductsByValue(sortCriteria);
            List<String> inventoryItemNameTexts = ListUtils.getElementsText(inventoryPage.getInventoryItemNames());
            List<Float> inventoryItemPriceFloats = ListUtils.getElementsPrice(inventoryPage.getInventoryItemPrices());

            switch (sortCriteria) {
                case "az":
                    assertThat(ListUtils.isSorted(inventoryItemNameTexts)).as("Products are sorted alphabetically").isTrue();
                    break;
                case "za":
                    assertThat(ListUtils.isReverseSorted(inventoryItemNameTexts)).as("Products are sorted from Z to A").isTrue();
                    break;
                case "lohi":
                    assertThat(ListUtils.isSorted(inventoryItemPriceFloats)).as("Products are sorted by price in ascending order").isTrue();
                    break;
                case "hilo":
                    assertThat(ListUtils.isReverseSorted(inventoryItemPriceFloats)).as("Products are sorted by price in descending order").isTrue();
                    break;
                default:
                    logger.warning("Unknown sort option: " + sortOption);
                    break;
            }
        }
    }

    private void addProductsFromTableToCart(Table table, InventoryPage inventoryPage) {
        for (TableRow row : table.getTableRows()) {
            String productName = row.getCell("Product Name");
            logger.info("Adding product " + productName + " to cart");
            inventoryPage.addProductToCart(productName);
        }
    }

    private InventoryPage logIn() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        return loginPage.logIn("standard_user", "secret_sauce");
    }
}

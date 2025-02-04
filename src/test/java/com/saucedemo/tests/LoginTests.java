package com.saucedemo.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.saucedemo.pageobjects.LoginPage;
import com.saucedemo.pageobjects.InventoryPage;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests {
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

    @Step("Login as standard user")
    public void testLoginFunctionality() {
        logger.info("Starting testLoginFunctionality");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        InventoryPage inventoryPage = loginPage.logIn("standard_user", "secret_sauce");
        inventoryPage.load();

        logger.info("Verify the login functionality");
        assertThat(inventoryPage.isLogoutButtonDisplayed()).isTrue();
        assertThat(inventoryPage.getCurrentUrl()).isEqualTo("https://www.saucedemo.com/inventory.html");
    }

    @Step("Try to log in with invalid credentials and fail")
    public void testLoginFunctionalityWithInvalidCredentials() {
        logger.info("Starting testLoginFunctionalityWithInvalidCredentials");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.logIn("nonexistent_user", "secret_sauce");

        logger.info("Verify the login functionality with invalid credentials");
        assertThat(loginPage.getErrorMessage()).isEqualTo("Epic sadface: Username and password do not match any user in this service");
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
        logger.info("Browser is closed");
    }
}

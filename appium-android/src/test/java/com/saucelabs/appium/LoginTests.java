package com.saucelabs.appium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        loginPage.waitForPageToLoad();
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(homePage.isInventoryVisible(), "Inventory page was not displayed after login.");
    }

    @Test
    public void testInvalidCredentials() {
        loginPage.waitForPageToLoad();
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for invalid credentials."
        );
    }
}

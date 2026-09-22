package com.saucelabs.appium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeLoginTests extends BaseTest {

    @Test
    public void testInvalidPassword() {
        loginPage.waitForPageToLoad();
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for invalid password."
        );
    }

    @Test
    public void testInvalidUsername() {
        loginPage.waitForPageToLoad();
        loginPage.login("invalid_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for invalid username."
        );
    }

    @Test
    public void testEmptyUsernameAndPassword() {
        loginPage.waitForPageToLoad();
        loginPage.login("", "");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for empty credentials."
        );
    }

    @Test
    public void testWhitespaceCredentials() {
        loginPage.waitForPageToLoad();
        loginPage.login("   ", "   ");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for whitespace credentials."
        );
    }

    @Test
    public void testEmojiUsername() {
        loginPage.waitForPageToLoad();
        loginPage.login("😀😀😀", "secret_sauce");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for emoji username."
        );
    }

    @Test
    public void testSqlInjectionAttempt() {
        loginPage.waitForPageToLoad();
        loginPage.login("'; DROP TABLE users; --", "secret_sauce");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for SQL-like input."
        );
    }

    @Test
    public void testVeryLongUsername() {
        loginPage.waitForPageToLoad();
        String longUsername = "a".repeat(300);

        loginPage.login(longUsername, "secret_sauce");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed for very long username."
        );
    }

    @Test
    public void testLockedOutUser() {
        loginPage.waitForPageToLoad();
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Locked-out error message was not displayed.");
    }
}

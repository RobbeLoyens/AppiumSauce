package com.saucelabs.appium;
import com.saucelabs.appium.ShopflowBaseTest;
import com.saucelabs.appium.models.Product;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.BrowserType;
public class WebProductClient {

    public Product getBackpackData() {

        try (Playwright playwright = Playwright.create()) {

            Browser browser =
                    playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(true));

            Page page = browser.newPage();

            page.navigate("https://www.saucedemo.com");

            page.locator("#user-name")
                    .fill("standard_user");

            page.locator("#password")
                    .fill("secret_sauce");

            page.locator("#login-button")
                    .click();

            String name =
                    page.locator(
                                    "[data-test='inventory-item-name']")
                            .first()
                            .innerText();

            String price =
                    page.locator(
                                    "[data-test='inventory-item-price']")
                            .first()
                            .innerText();

            String description =
                    page.locator(
                                    "[data-test='inventory-item-desc']")
                            .first()
                            .innerText();

            browser.close();

            return new Product(
                    name,
                    price);
        }
    }
}
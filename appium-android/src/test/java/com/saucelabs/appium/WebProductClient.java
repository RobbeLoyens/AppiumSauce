package com.saucelabs.appium;

import com.microsoft.playwright.*;
import com.saucelabs.appium.models.Product;

public class WebProductClient {

    public Product getProduct() {

        try (Playwright playwright = Playwright.create()){

        Browser browser =
                   playwright.chromium()
                .launch(
                       new BrowserType.LaunchOptions()
                             .setHeadless(true));
            Page page = browser.newPage();

        page.navigate("https://www.saucedemo.com");

           page.locator("#user-name").fill("standard_user");

        page.locator("#password")
                    .fill("secret_sauce");

            page.locator("#login-button")
                             .click();

        String name =
                 page.locator(".inventory_item_name")
                .first()
                .textContent();

        String price =
                page.locator(".inventory_item_price")
                        .first()
                        .textContent();

        browser.close();

        return new Product(
                name,
                price
        );
        }
    }
}
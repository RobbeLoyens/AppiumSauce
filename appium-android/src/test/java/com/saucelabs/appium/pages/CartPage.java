package com.saucelabs.appium.pages;

import com.saucelabs.appium.models.Product;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private final By cartProductName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Bike Light']");

    private final By cartProductPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$9.99']");
    private final By backpackName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']");

    private final By backpackPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$29.99']");

    private final By bikeLightName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Bike Light']");

    private final By bikeLightPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$9.99']");
    private final By removeBackpackButton =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Backpack']/following::android.view.ViewGroup[@content-desc='test-REMOVE'][1]"
            );
    private final By continueShoppingButton =
            AppiumBy.accessibilityId("test-CONTINUE SHOPPING");
    private final By removeBikeLightButton =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Bike Light']/following::android.view.ViewGroup[@content-desc='test-REMOVE'][1]"
            );

    public CartPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public Product getProductInCart() {

        return new Product(
                driver.findElement(cartProductName).getText(),
                driver.findElement(cartProductPrice).getText()
        );
    }
    public Product getBackpackInCart() {

        return new Product(
                driver.findElement(backpackName).getText(),
                driver.findElement(backpackPrice).getText()
        );
    }
    public Product getBikeLightInCart() {

        return new Product(
                driver.findElement(bikeLightName).getText(),
                driver.findElement(bikeLightPrice).getText()
        );
    }
    public boolean isBackpackInCart() {
        return !driver.findElements(backpackName).isEmpty();
    }
    public boolean isBikeLightInCart() {
        return !driver.findElements(bikeLightName).isEmpty();
    }
    public void removeBackpack() {
        driver.findElement(removeBackpackButton).click();
    }
    public void removeBikeLight() {
        driver.findElement(removeBikeLightButton).click();
    }

    public void continueShopping() {
        driver.findElement(continueShoppingButton).click();
    }
}

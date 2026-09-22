package com.saucelabs.appium.pages;

import com.saucelabs.appium.models.Product;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private final By productsTitle = AppiumBy.accessibilityId("test-PRODUCTS");
    private final By backpackName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']");

    private final By backpackPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$29.99']");
    private final By backpackAddToCart =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Backpack']/following::android.view.ViewGroup[@content-desc='test-ADD TO CART'][1]"
            );

    private final By cartBadge =
            AppiumBy.accessibilityId("test-Cart");
    private final By bikeLightAddToCart =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Bike Light']/following::android.view.ViewGroup[@content-desc='test-ADD TO CART'][1]"
            );
    private final By cartBadgeCount =
            AppiumBy.xpath(
                    "//android.view.ViewGroup[@content-desc='test-Cart']" +
                            "//android.widget.TextView"
            );
    private final By backpackInCart =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Backpack']"
            );
    private final By bikeLightInCart =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Bike Light']"
            );

    private final By removeBackpackButton =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Backpack']/following::android.view.ViewGroup[@content-desc='test-REMOVE'][1]"
            );

    private final By removeBikeLightButton =
            AppiumBy.xpath(
                    "//android.widget.TextView[@text='Sauce Labs Bike Light']/following::android.view.ViewGroup[@content-desc='test-REMOVE'][1]"
            );

    private final By bikeLightName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Bike Light']");

    private final By bikeLightPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$9.99']");



    public ProductPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductsPageVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productsTitle)
        ).isDisplayed();
    }

    public void addBackpackToCart() {
        driver.findElement(backpackAddToCart).click();
    }

    public boolean isCartBadgeVisible() {
        return driver.findElement(cartBadge).isDisplayed();
    }

    public boolean addBikeLightToCart() {
        driver.findElement(bikeLightAddToCart).click();
        return true;
    }

    public int getCartBadgeCount() {
        String badgeCount =
                driver.findElement(cartBadgeCount)
                        .getAttribute("text");
        System.out.println("Badge count: " + badgeCount);
        return Integer.parseInt(badgeCount);
    }
    public boolean isBackpackInCart() {
        return driver.findElement(backpackInCart).isDisplayed();
    }


    public boolean isBikeLightInCart() {
        return driver.findElement(bikeLightInCart).isDisplayed();
    }
    public boolean wasBackpackRemoved() {
        return driver.findElements(backpackInCart).isEmpty();
    }
    public boolean openCart() {
        driver.findElement(cartBadge).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.xpath("//android.widget.TextView[@text='YOUR CART']")
                )
        );


        return true;
    }

    public boolean removeBackpack() {
        driver.findElement(removeBackpackButton).click();
        return true;
    }
    public boolean removeBikeLight() {
        driver.findElement(removeBikeLightButton).click();
        return true;
    }


    public Product getBikeLight() {

        return new Product(
                driver.findElement(bikeLightName).getText(),
                driver.findElement(bikeLightPrice).getText()
        );
    }

    public Product getBackpack() {

        return new Product(
                driver.findElement(backpackName).getText(),
                driver.findElement(backpackPrice).getText()
        );
    }


}
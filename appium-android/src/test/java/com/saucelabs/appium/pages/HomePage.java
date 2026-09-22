package com.saucelabs.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class HomePage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private final By productsView = AppiumBy.accessibilityId("test-PRODUCTS");

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isInventoryVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productsView)).isDisplayed();
    }
}

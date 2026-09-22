package com.saucelabs.appium.pages;

import com.saucelabs.appium.models.Product;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class CheckoutOverviewPage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final By overviewProductName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Bike Light']");

    private final By overviewProductPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$9.99']");

    public CheckoutOverviewPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public Product getOverviewProduct() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        overviewProductName
                )
        );

        System.out.println("Reached overview page");

        return new Product(
                driver.findElement(overviewProductName).getText(),
                driver.findElement(overviewProductPrice).getText()
        );
    }

}

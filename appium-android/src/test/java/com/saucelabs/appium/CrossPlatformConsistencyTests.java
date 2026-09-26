package com.saucelabs.appium;

import com.saucelabs.appium.models.Product;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
public class CrossPlatformConsistencyTests
        extends ShopflowBaseTest {

    @Test
    public void verifyBackpackConsistencyBetweenWebAndMobile() {

        WebProductClient webClient =
                new WebProductClient();

        Product webProduct =
                webClient.getBackpackData();

        Product mobileProduct =
                productPage.getBackpackData();

        Assert.assertEquals(
                mobileProduct.name(),
                webProduct.name(),
                "Product names should match");

        Assert.assertEquals(
                mobileProduct.price(),
                webProduct.price(),
                "Product prices should match");

    }
}

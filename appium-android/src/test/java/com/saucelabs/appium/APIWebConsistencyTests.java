package com.saucelabs.appium;

import com.saucelabs.appium.models.Product;
import com.saucelabs.appium.api.ProductApiClient;
import com.saucelabs.appium.WebProductClient;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
public class APIWebConsistencyTests {
    @Test
    public void  verifyApiAndWebsiteConsistency() {

         ProductApiClient apiClient =
                new ProductApiClient();

        WebProductClient webClient =
                new WebProductClient();

        Product apiProduct =
                apiClient.getProduct();

        Product webProduct =
                webClient.getProduct();

        System.out.println("API: " + apiProduct);
        System.out.println("WEB: " + webProduct);
    }
}

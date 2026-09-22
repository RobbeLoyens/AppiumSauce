package com.saucelabs.appium.pages;

import com.saucelabs.appium.models.Product;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final By checkoutButton =
            AppiumBy.accessibilityId("test-CHECKOUT");

    private final By firstNameField =
            AppiumBy.accessibilityId("test-First Name");

    private final By lastNameField =
            AppiumBy.accessibilityId("test-Last Name");

    private final By postalCodeField =
            AppiumBy.accessibilityId("test-Zip/Postal Code");

    private final By continueButton =
            AppiumBy.accessibilityId("test-CONTINUE");

    private final By finishButton =
            AppiumBy.accessibilityId("test-FINISH");

    private final By orderCompleteMessage =
            AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: OVERVIEW']");

    private final By orderErrorMessage =
            AppiumBy.accessibilityId("test-Error message");

    private final By checkoutInformationTitle =
            AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: INFORMATION']");

    private final By overviewProductName =
            AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Bike Light']");

    private final By overviewProductPrice =
            AppiumBy.xpath("//android.widget.TextView[@text='$9.99']");
       public CheckoutPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void startCheckout() {
        driver.findElement(checkoutButton).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        checkoutInformationTitle
                )
        );
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode){
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        driver.findElement(continueButton).click();
    }

    public boolean isOrderCompleted() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderCompleteMessage)
        ).isDisplayed();
    }

    public boolean isErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderErrorMessage)
        ).isDisplayed();
    }

    public Product getOverviewProduct() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        overviewProductName
                )
        );

        return new Product(
                driver.findElement(overviewProductName).getText(),
                driver.findElement(overviewProductPrice).getText()
        );
    }
}

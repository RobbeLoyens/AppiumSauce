package com.saucelabs.appium;

import com.saucelabs.appium.config.AppiumConfig;
import com.saucelabs.appium.pages.CheckoutPage;
import com.saucelabs.appium.pages.HomePage;
import com.saucelabs.appium.pages.LoginPage;
import com.saucelabs.appium.pages.ProductPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    protected AppiumDriver driver;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected ProductPage productPage;
    protected CheckoutPage checkoutPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(AppiumConfig.getPlatformName())
                .setAutomationName(AppiumConfig.getAutomationName())
                .setDeviceName(AppiumConfig.getDeviceName())
                .setApp(AppiumConfig.getAppPath())
                .setAppPackage(AppiumConfig.getAppPackage())
                .setAppActivity(AppiumConfig.getAppActivity())
                .fullReset();

        String serverUrl = AppiumConfig.getServerUrl();
        driver = new AndroidDriver(new URL(serverUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppiumConfig.getImplicitWaitSeconds()));

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package com.saucelabs.appium;

import com.saucelabs.appium.models.Product;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.saucelabs.appium.pages.CartPage;

public class DataConsistencyTests extends ShopflowBaseTest {

    @BeforeMethod
    public void ensureCleanState() {

        int attempts = 0;

        while (!productPage.isProductsPageDisplayed() && attempts < 5) {
            driver.navigate().back();
            attempts++;
        }

        if (productPage.getCartBadgeCount() > 0) {
            productPage.openCart();

            cartPage.removeBikeLightIfPresent();
            cartPage.removeBackpackIfPresent();

            cartPage.continueShopping();
        }
    }
    @Test
    public void verifyProductInformationConsistencyAcrossPages() {

        Product expectedProduct =
                productPage.getBikeLight();

        productPage.addBikeLightToCart();
        productPage.openCart();

        Product cartProduct =
                cartPage.getProductInCart();

        Assert.assertEquals(
                cartProduct,
                expectedProduct,
                "Product information should remain consistent in cart."
        );

        checkoutPage.startCheckout();

        checkoutPage.fillCheckoutForm(
                "Robbe",
                "Tester",
                "GZR1000"
        );

        Product overviewProduct =
                checkoutPage.getOverviewProduct();

        Assert.assertEquals(
                overviewProduct,
                expectedProduct,
                "Product information should remain consistent in checkout."
        );
    }

    @Test
    public void verifyCartStateConsistencyDuringNavigation() {

        Product expectedBikeLight =
                productPage.getBikeLight();

        Product expectedBackpack =
                productPage.getBackpack();

        productPage.addBikeLightToCart();
        productPage.addBackpackToCart();

        productPage.openCart();

        Product bikeLightInCart =
                cartPage.getBikeLightInCart();

        Product backpackInCart =
                cartPage.getBackpackInCart();

        Assert.assertEquals(
                bikeLightInCart,
                expectedBikeLight,
                "Bike Light should be present before navigation."
        );

        Assert.assertEquals(
                backpackInCart,
                expectedBackpack,
                "Backpack should be present before navigation."
        );

        cartPage.continueShopping();

        productPage.openCart();

        Product bikeLightAfterNavigation =
                cartPage.getBikeLightInCart();

        Product backpackAfterNavigation =
                cartPage.getBackpackInCart();

        Assert.assertEquals(
                bikeLightAfterNavigation,
                expectedBikeLight,
                "Bike Light should remain consistent after navigation."
        );

        Assert.assertEquals(
                backpackAfterNavigation,
                expectedBackpack,
                "Backpack should remain consistent after navigation."
        );
    }
    @Test
    public void verifyCartBadgeRemainsConsistentDuringNavigation() {

        productPage.addBikeLightToCart();
        productPage.addBackpackToCart();

        int badgeCount = productPage.getCartBadgeCount();

        productPage.openCart();

        int cartItemCount = cartPage.getCartItemCount();

        Assert.assertEquals(
                cartItemCount,
                badgeCount,
                "Cart item count should match badge count");
    }
}

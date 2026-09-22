package com.saucelabs.appium;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ShopTests extends ShopflowBaseTest {

    @Test
    public void testCompleteShoppingFlow() {

        Assert.assertTrue(
                productPage.isProductsPageVisible(),
                "Products page was not displayed."
        );

        productPage.addBackpackToCart();

        Assert.assertTrue(
                productPage.isCartBadgeVisible(),
                "Cart badge did not appear after adding a product."
        );

        productPage.addBikeLightToCart();

        Assert.assertEquals(
                productPage.getCartBadgeCount(),
                2,
                "Cart badge should display 2 products."
        );

        productPage.openCart();

        Assert.assertTrue(
                productPage.isBackpackInCart(),
                "Backpack should be visible in cart."
        );

        Assert.assertTrue(
                productPage.isBikeLightInCart(),
                "Bike Light should be visible in cart."
        );

        productPage.removeBackpack();

        Assert.assertTrue(
                productPage.wasBackpackRemoved(),
                "Backpack should have been removed."
        );

        checkoutPage.startCheckout();

        checkoutPage.fillCheckoutForm("", "", "");

        Assert.assertTrue(
                checkoutPage.isErrorDisplayed(),
                "Validation error should be displayed."
        );

        checkoutPage.fillCheckoutForm(
                "Robbe",
                "Loyens",
                "GZR1000"
        );

        Assert.assertTrue(
                checkoutPage.isOrderCompleted(),
                "Order confirmation should be displayed."
        );
    }
}
package com.saucelabs.appium.api;

import com.saucelabs.appium.models.Product;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductApiClient {

    public Product getProduct() {

        Response response =
                RestAssured
                        .given()
                        .baseUri("https://dummyjson.com")
                        .when()
                        .get("/products/1");

        String name =
                response.jsonPath()
                        .getString("title");

        String price =
                String.valueOf(
                        response.jsonPath()
                                .getDouble("price"));

        return new Product(
                name,
                price
        );
    }
}
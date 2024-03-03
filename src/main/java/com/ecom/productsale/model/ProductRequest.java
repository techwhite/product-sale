package com.ecom.productsale.model;

import lombok.ToString;

/*
 DTO model used for mvc layer
 */

@ToString
public class ProductRequest {
    // product name
    private String name;
    // product price
    private float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

package com.lightspeed.practicaltest.model;

import lombok.ToString;

import java.math.BigDecimal;

// model used for mvc layer

@ToString
public class ProductRequest {
    // product name
    private String name;
    // product price
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

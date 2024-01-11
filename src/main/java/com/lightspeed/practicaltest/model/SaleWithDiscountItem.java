package com.lightspeed.practicaltest.model;

import java.math.BigDecimal;

// model used for mvc layer

public class SaleWithDiscountItem {
    // product id
    private Integer id;
    // revenue for each item
    private BigDecimal revenue;
    // discount used
    private Double discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SaleWithDiscountItem{" +
                "id=" + id +
                ", revenue=" + revenue +
                ", discount=" + discount +
                '}';
    }
}

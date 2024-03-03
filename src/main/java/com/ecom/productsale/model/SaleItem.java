package com.ecom.productsale.model;

import java.math.BigDecimal;

/*
 model used for mvc layer
 */

public class SaleItem {
    // product id
    private Integer id;

    // total price
    private BigDecimal revenue;

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", revenue=" + revenue +
                '}';
    }

}

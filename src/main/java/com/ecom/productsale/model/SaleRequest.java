package com.ecom.productsale.model;

/*
 model used for mvc layer
 */

public class SaleRequest {
    // product id
    private Integer id;

    @Override
    public String toString() {
        return "SaleRequest{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // quantity of product
    private Integer quantity;
}

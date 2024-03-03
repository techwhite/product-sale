package com.ecom.productsale.model;

import java.util.List;

/*
 model used for mvc layer
 */

public class SaleWithDiscountRequest {
    // discount for all products sale
    private Double total_discount;
    // SaleRequst list
    private List<SaleRequest> requestList;

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }

    public List<SaleRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<SaleRequest> requestList) {
        this.requestList = requestList;
    }

    @Override
    public String toString() {
        return "SaleWithDiscountRequest{" +
                "total_discount=" + total_discount +
                ", requestList=" + requestList +
                '}';
    }
}

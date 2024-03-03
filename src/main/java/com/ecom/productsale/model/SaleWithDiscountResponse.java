package com.ecom.productsale.model;

import java.math.BigDecimal;
import java.util.List;

/*
 model used for mvc layer
 */

public class SaleWithDiscountResponse {
    // entire request
    private SaleWithDiscountRequest request;
    // Sale with discount item list
    private List<SaleWithDiscountItem> discountItemList;
    // total revenue for all sale
    private BigDecimal total_revenue;

    public SaleWithDiscountRequest getRequest() {
        return request;
    }

    public void setRequest(SaleWithDiscountRequest request) {
        this.request = request;
    }

    public List<SaleWithDiscountItem> getDiscountItemList() {
        return discountItemList;
    }

    public void setDiscountItemList(List<SaleWithDiscountItem> discountItemList) {
        this.discountItemList = discountItemList;
    }

    public BigDecimal getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(BigDecimal total_revenue) {
        this.total_revenue = total_revenue;
    }

    @Override
    public String toString() {
        return "SaleWithDiscountResponse{" +
                "request=" + request +
                ", discountItemList=" + discountItemList +
                ", total_revenue=" + total_revenue +
                '}';
    }
}

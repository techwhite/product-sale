package com.ecom.productsale.model;

import java.math.BigDecimal;
import java.util.List;

/*
 model used for mvc layer
 */

public class SaleResponse {

    // entire request
    private List<SaleRequest> requestList;

    // total price for each item
    private List<SaleItem> total_price_item;

    // total price for the all sale
    private BigDecimal total_price;

    public List<SaleItem> getTotal_price_item() {
        return total_price_item;
    }

    public void setTotal_price_item(List<SaleItem> total_price_item) {
        this.total_price_item = total_price_item;
    }

    public List<SaleRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<SaleRequest> requestList) {
        this.requestList = requestList;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "SaleResponse{" +
                "requestList=" + requestList +
                ", total_price_item=" + total_price_item +
                ", total_price=" + total_price +
                '}';
    }

}

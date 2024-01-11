package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.ProductEntity;
import com.lightspeed.practicaltest.model.SaleItem;
import com.lightspeed.practicaltest.model.SaleRequest;
import com.lightspeed.practicaltest.model.SaleResponse;
import com.lightspeed.practicaltest.model.SaleWithDiscountItem;
import com.lightspeed.practicaltest.model.SaleWithDiscountRequest;
import com.lightspeed.practicaltest.model.SaleWithDiscountResponse;
import com.lightspeed.practicaltest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// This class implements the core business logic for sale process

@Service
public class SaleService {
    @Autowired
    private ProductRepository productRepository;

    // basic sale process: accumulate total revenue for each item and all items
    public SaleResponse sale(List<SaleRequest> requestList) {

        // calculate total revenue for each product id and all
        List<SaleItem> itemList = new ArrayList<>();
        BigDecimal total_revenue = new BigDecimal(0);
        for (int i = 0; i < requestList.size(); i++) {
            SaleRequest productReq = requestList.get(i);
            Optional<ProductEntity> entity = productRepository.findById(productReq.getId());
            if (!entity.isPresent()) {
                throw new RuntimeException("Don't have product detail in db for product: " + productReq.getId());
            }
            BigDecimal price = entity.get().getPrice();

            BigDecimal revenue = price.multiply(BigDecimal.valueOf(productReq.getQuantity()));
            total_revenue = total_revenue.add(revenue);

            SaleItem si = new SaleItem();
            si.setId(productReq.getId());
            si.setRevenue(revenue);
            itemList.add(si);
        }

        // return
        SaleResponse response = new SaleResponse();
        response.setRequestList(requestList);
        response.setTotal_price_item(itemList);
        response.setTotal_price(total_revenue);

        return response;
    }


    // sale process with discount
    public SaleWithDiscountResponse saleWithDiscount(SaleWithDiscountRequest request) {
        Double total_discount = request.getTotal_discount();
        List<SaleRequest> requestList = request.getRequestList();

        // calculate total revenue for each product id and all
        List<SaleWithDiscountItem> itemList = new ArrayList<>();
        BigDecimal total_revenue = new BigDecimal(0);
        for (int i = 0; i < requestList.size(); i++) {
            SaleRequest productReq = requestList.get(i);
            Optional<ProductEntity> entity = productRepository.findById(productReq.getId());
            if (!entity.isPresent()) {
                throw new RuntimeException("Don't have product detail in db for product: " + productReq.getId());
            }
            BigDecimal price = entity.get().getPrice();

            BigDecimal revenue = price.multiply(BigDecimal.valueOf(productReq.getQuantity()));
            total_revenue = total_revenue.add(revenue);

            SaleWithDiscountItem si = new SaleWithDiscountItem();
            si.setId(productReq.getId());
            si.setRevenue(revenue);
            itemList.add(si);
        }

        // calculate discount and new revenue for each product
        Double discount_left = total_discount;
        for (int i = 0; i < itemList.size()-1; i++) {
            SaleWithDiscountItem item = itemList.get(i);

            Double discount_used = item.getRevenue().doubleValue() / total_revenue.doubleValue() * total_discount;
            discount_left = discount_left - discount_used;
            item.setDiscount(discount_used);
            item.setRevenue(item.getRevenue().subtract(BigDecimal.valueOf(discount_used)));
        }
        SaleWithDiscountItem discountItem = itemList.get(itemList.size()-1);
        discountItem.setDiscount(discount_left);
        discountItem.setRevenue(discountItem.getRevenue().subtract(BigDecimal.valueOf(discount_left)));

        // return
        SaleWithDiscountResponse response = new SaleWithDiscountResponse();
        response.setRequest(request);
        response.setDiscountItemList(itemList);
        response.setTotal_revenue(total_revenue.subtract(BigDecimal.valueOf(total_discount)));

        return response;
    }
}

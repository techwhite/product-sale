package com.ecom.productsale.controller;

import com.ecom.productsale.interceptor.RateLimit;
import com.ecom.productsale.model.SaleRequest;
import com.ecom.productsale.model.SaleResponse;
import com.ecom.productsale.model.SaleWithDiscountRequest;
import com.ecom.productsale.model.SaleWithDiscountResponse;
import com.ecom.productsale.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 provide restful api endpoints for sale processing
 */

@RateLimit(value = 5.0) // support 5 request per second
@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    /*
     stage 3 API endpoint: allows sales to be made. It is not necessary to persist sales.
     */
    @PostMapping
    public ResponseEntity<Object> saleProduct(@RequestBody List<SaleRequest> requestList) {
        // request check
        if (requestList == null || requestList.isEmpty()) {
            return ResponseEntity.badRequest().body("Request is Empty");
        }

        // core call
        SaleResponse response = null;
        try {
            response = saleService.sale(requestList);
        } catch (RuntimeException e) {
            return ResponseEntity.ok().body(e);
        }

        return ResponseEntity.ok().body(response);
    }


    /*
     stage 4 API endpoint: allow discounts on the overall sale.
     */
    @PostMapping("/discount")
    public ResponseEntity<Object> saleWithDiscount(@RequestBody SaleWithDiscountRequest request) {
        // request data check
        if (request == null || request.getTotal_discount() <= 0 || request.getRequestList().size() == 0) {
            return ResponseEntity.badRequest().body("Total_Discount Invalid or RequestList is empty");
        }

        // core logic call
        SaleWithDiscountResponse response = null;
        try {
            response = saleService.saleWithDiscount(request);
        } catch (RuntimeException e) {
            return ResponseEntity.ok().body(e);
        }

        return ResponseEntity.ok().body(response);
    }
}

package com.lightspeed.practicaltest.controller;

import com.lightspeed.practicaltest.interceptor.RateLimit;
import com.lightspeed.practicaltest.model.ErrorResponse;
import com.lightspeed.practicaltest.model.SaleRequest;
import com.lightspeed.practicaltest.model.SaleResponse;
import com.lightspeed.practicaltest.model.SaleWithDiscountRequest;
import com.lightspeed.practicaltest.model.SaleWithDiscountResponse;
import com.lightspeed.practicaltest.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// provide restful api endpoints for sale processing

@RateLimit(value = 5.0) // support 5 request per second
@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    // stage 3 API endpoint: allows sales to be made. It is not necessary to persist sales.
    @PostMapping
    public ResponseEntity<?> saleProduct(@RequestBody List<SaleRequest> requestList) {
        // request check
        if (requestList == null || requestList.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Request is Empty", "",
                    requestList), HttpStatus.BAD_REQUEST);
        }

        // core call
        SaleResponse response = null;
        try {
            response = saleService.sale(requestList);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK, e.getMessage()), HttpStatus.OK);
        }

        return ResponseEntity.ok().body(response);
    }


    // stage 4 API endpoint: allow discounts on the overall sale.
    @PostMapping("/discount")
    public ResponseEntity<?> saleWithDiscount(@RequestBody SaleWithDiscountRequest request) {
        // request data check
        if (request == null || request.getTotal_discount() <= 0 || request.getRequestList().size() == 0) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Total_Discount Invalid or " +
                    "RequestList is empty", "", request), HttpStatus.BAD_REQUEST);
        }

        // core logic call
        SaleWithDiscountResponse response = null;
        try {
            response = saleService.saleWithDiscount(request);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK, e.getMessage()), HttpStatus.OK);
        }

        return ResponseEntity.ok().body(response);
    }
}

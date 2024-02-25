package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.SaleRequest;
import com.lightspeed.practicaltest.model.SaleResponse;
import com.lightspeed.practicaltest.model.SaleWithDiscountRequest;
import com.lightspeed.practicaltest.model.SaleWithDiscountResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SaleServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceTest.class);

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Test
    public void testSale() {
        // mock db data

        List<SaleRequest> requestList = new ArrayList<>();
        SaleRequest request = new SaleRequest();
        request.setId(11);
        request.setQuantity(2);
        requestList.add(request);
        SaleRequest request1 = new SaleRequest();
        request1.setId(13);
        request1.setQuantity(1);
        requestList.add(request1);

        SaleResponse res = null;
        try {
            res = saleService.sale(requestList);
        } catch (Exception e) {
//            fail(e);
        }
        logger.debug("testSale:{}", res);

        // remove test data
    }

    @Test void testSalewithDiscount() {
        // todo: mock data

        SaleWithDiscountRequest req = new SaleWithDiscountRequest();
        req.setTotal_discount(10.0);

        List<SaleRequest> requestList = new ArrayList<>();
        SaleRequest request = new SaleRequest();
        request.setId(11);
        request.setQuantity(2);
        requestList.add(request);
        SaleRequest request1 = new SaleRequest();
        request1.setId(13);
        request1.setQuantity(1);
        requestList.add(request1);
        req.setRequestList(requestList);

        SaleWithDiscountResponse res = null;
        try {
            res = saleService.saleWithDiscount(req);
        } catch (Exception e) {
//            fail(e);
        }

        logger.debug("testSalewithdiscount:{}", res);

        // remove mock data
    }

}

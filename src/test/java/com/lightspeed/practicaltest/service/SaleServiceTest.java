package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.SaleRequest;
import com.lightspeed.practicaltest.model.SaleWithDiscountRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SaleServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceTest.class);

    @Autowired
    private SaleService saleService;

    @Test
    public void testSale() {
        List<SaleRequest> requestList = new ArrayList<>();
        SaleRequest request = new SaleRequest();
        request.setId(11);
        request.setQuantity(2);
        requestList.add(request);
        SaleRequest request1 = new SaleRequest();
        request1.setId(13);
        request1.setQuantity(1);
        requestList.add(request1);

        logger.debug("testSale:{}", saleService.sale(requestList));

    }

    @Test void testSalewithDiscount() {
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

        logger.debug("testSalewithdiscount:{}", saleService.saleWithDiscount(req));
    }

}

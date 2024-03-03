package com.ecom.productsale.proxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.SSLSocketFactory;

import static org.junit.jupiter.api.Assertions.*;

/*
Defines test method for methods in VisaServiceProxy class
 */
@SpringBootTest
public class HttpUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtilTest.class);

    @Autowired
    private HttpUtil util;

    @Test
    public void testGetSSLSocketFactory() {
        SSLSocketFactory factory = null;
        try{
            factory = util.getSSLContext().getSocketFactory();
        } catch (Exception e) {
            fail(e);
        }

        assertNotNull(factory);
    }

    @Test
    public void testGetAuthorization() {
        String expected = "Basic " +
                "RUNFWE9DSDNEOUNWVzFJNkFIM0kyMTRlc1JOU0tVaVUzV25Cb2o2TWtYdllkWFJFSTpDZm51MzI4Ulk2WWZ4V1M2VA==";
        String auth = util.getAuthorization();
        assertEquals(expected, auth);
    }

    // case: bad httpconnection
    @Test
    public void testGetResponseContent() {
        String res = null;
        try {
            res = util.getResponseContent(null, 200);
        } catch (Exception e) {
            fail(e);
        }
        assertNull(res);
    }
}

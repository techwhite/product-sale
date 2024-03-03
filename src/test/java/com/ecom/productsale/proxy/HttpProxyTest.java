package com.ecom.productsale.proxy;

import com.ecom.productsale.model.VisaResponse;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/*
Defines test method for methods in VisaServiceProxy class
 */
@SpringBootTest
public class HttpProxyTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpProxyTest.class);

    @Value("${visa.service.endpoint.url}")
    private String endpointUrl;

    @Autowired
    private HttpURLConnectionProxy proxy;

    @Autowired
    private HttpSyncClientProxy syncClientProxy;

    @Autowired
    private HttpAsyncClientProxy asyncClientProxy;

    @Test
    public void testHelloWord() {
        try {
            VisaResponse res = proxy.helloWorld(endpointUrl);
            assertTrue(res != null && res.getMessage().equals("helloworld"));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testExecuteHttpSync() {
        try {
            VisaResponse res = syncClientProxy.execute(endpointUrl);
            System.out.println(res);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testExecuteHttpASync() throws InterruptedException {
        try {
            VisaResponse res = asyncClientProxy.execute(endpointUrl);
            System.out.println(res);
        } catch (Exception e) {
            fail(e);
        }
    }
}

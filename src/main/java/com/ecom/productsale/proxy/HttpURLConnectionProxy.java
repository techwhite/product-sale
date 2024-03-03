package com.ecom.productsale.proxy;

import com.ecom.productsale.model.VisaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
HttpURLConnectionProxy is a layer to interact with dependent service.
Here is external visa service. The url is defined as visa.service.endpoint.url in application.properties

Using completableFuture and httpAsync is duplicated operation. should only keep one of them.
 */
@Component
public class HttpURLConnectionProxy {
    private static final Logger logger = LoggerFactory.getLogger(HttpURLConnectionProxy.class);
    private final ExecutorService executorService;

    @Autowired
    private HttpUtil util;

    public HttpURLConnectionProxy() {
        // ThreadPool configuration
        this.executorService = Executors.newFixedThreadPool(10); // fixed size
//        this.executorService = Executors.newSingleThreadExecutor();
//        this.executorService = Executors.newCachedThreadPool();
    }

    public VisaResponse helloWorld(String url) throws Exception {
        logger.debug("START Two-Way (Mutual) SSL ...");

        CompletableFuture<VisaResponse> future = CompletableFuture.supplyAsync(() -> {
            try {
                return request(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        logger.debug("END Two-Way (Mutual) SSL ...");

        return future.get();
    }

    private VisaResponse request(String endpointUrl) throws Exception {
        // open http connection
        URL url = new URL(endpointUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // cast to https type
        if (con instanceof HttpsURLConnection) {
            ((HttpsURLConnection) con).setSSLSocketFactory(util.getSSLContext().getSocketFactory());
        }

        // set header info
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", util.getAuthorization());

        // send
        int status = con.getResponseCode();
        if (status != 200) {
            logger.error("Two-Way (Mutual) SSL test failed, status:" + status);
        } else {
            logger.info("Http Status: " + status);
        }

        // parse response
        String result = util.getResponseContent(con, status);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        // the field name of java object should be same with json string's
        VisaResponse response = objectMapper.readValue(result, VisaResponse.class);

        con.disconnect();

        return response;
    }
}

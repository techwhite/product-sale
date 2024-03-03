package com.ecom.productsale.proxy;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
sync threadpool + async http
 */
@Component
public class HttpAsyncClientProxy {
    private static final Logger logger = LoggerFactory.getLogger(HttpAsyncClientProxy.class);

    private final ExecutorService executorService;
    private CloseableHttpAsyncClient httpAsyncClient;

    @Autowired
    private HttpUtil util;

    public HttpAsyncClientProxy() {
        // ThreadPool configuration
        this.executorService = Executors.newFixedThreadPool(10); // fixed size
//        this.executorService = Executors.newSingleThreadExecutor();
//        this.executorService = Executors.newCachedThreadPool();
    }

    /*
   since http client need to load resource and use httpUtil,
   it's better to do these in post construct stage
    */
    @PostConstruct
    public void init() throws Exception {
        // used for connecting manager connection
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setConnectTimeout(5000) // connect timeout
                .setSoTimeout(5000) // socket data reader timeout
                .build();
        DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        PoolingNHttpClientConnectionManager ncm = new PoolingNHttpClientConnectionManager(ioReactor);
        ncm.setMaxTotal(100);
        ncm.setDefaultMaxPerRoute(20);

        // security
        SSLIOSessionStrategy sslSessionStrategy = new SSLIOSessionStrategy(util.getSSLContext());

        // used for request connection
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // connect timeout
                .setSocketTimeout(5000) // data read timeout
                .setConnectionRequestTimeout(5000) // request timeout
                .build();

        // async don't support setting setRetryHandler() like sync. since it's async, better left user to process
        // retry logic
        this.httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(ncm)
                .setDefaultRequestConfig(requestConfig)
                .setSSLStrategy(sslSessionStrategy)
                .build();
        this.httpAsyncClient.start(); // start async client
    }

    /*
    submit is sync method, but http is async. So also has a good performance
     */
    public String execute(String url) throws InterruptedException, ExecutionException, IOException {
        Future<HttpResponse> future = executorService.submit(() -> {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Authorization", util.getAuthorization());

            return httpAsyncClient.execute(httpGet, null).get();
        });

        HttpResponse response = future.get();
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public void shutdown() {

        try {
            httpAsyncClient.close(); // 关闭异步客户端
            this.executorService.shutdown();
        } catch (IOException e) {
            logger.error("shutdown error: ", e);
        }
    }
}

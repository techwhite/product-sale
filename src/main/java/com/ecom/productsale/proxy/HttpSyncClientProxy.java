package com.ecom.productsale.proxy;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
async threadpool + sync http
 */
@Component
public class HttpSyncClientProxy {
    private static final Logger logger = LoggerFactory.getLogger(HttpSyncClientProxy.class);

    private CloseableHttpClient httpClient;
    private final ExecutorService executorService;

    @Autowired
    private HttpUtil util;

    public HttpSyncClientProxy() {
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
        // common configurations
        HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3, true); // retry 3 times

        // sync http client configuration
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(); // connection pool can increase usage rate
        cm.setMaxTotal(100); // max connection count
        cm.setDefaultMaxPerRoute(20); // mac connection per target host machine
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(util.getSSLContext());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // connect timeout
                .setSocketTimeout(5000) // data read timeout
                .setConnectionRequestTimeout(5000) // request timeout
                .build();
        this.httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(retryHandler)
                .setSSLSocketFactory(sslsf)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public String execute(String url) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
            try {
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Content-Type", "application/json");
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Authorization", util.getAuthorization());

                return httpClient.execute(httpGet);
            } catch (IOException e) {
                // just return outside
                throw new RuntimeException(e);
            }
        }, executorService);

        return EntityUtils.toString(future.get().getEntity(), "UTF-8");
    }

    /*
    async threadpool + sync runnable, don't have return value
     */
    public Object execute() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> (Runnable) () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);

        return future.get();
    }

    /*
    async thread pool + sync callable, have return value
     */
    public Callable<Integer> execute(int x, int y) throws ExecutionException, InterruptedException {
        CompletableFuture<Callable<Integer>> future = CompletableFuture.supplyAsync(() -> () -> x+y,
                executorService);

        return future.get();
    }

    public void shutdown() {
        try {
            executorService.shutdown();
            httpClient.close();
        } catch (IOException e) {
            logger.error("shutdown error: ", e);
        }
    }
}

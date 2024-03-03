package com.ecom.productsale.proxy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureProxy {
    public void asyncRequest() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "World";
        }, executor);

        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            return result1 + " " + result2;
        });

        System.out.println(combinedFuture.get()); // 输出：Hello World

        CompletableFuture<String>[] futures = new CompletableFuture[] {future2, future1};
        CompletableFuture.allOf(futures).get(100, TimeUnit.MICROSECONDS);
        CompletableFuture.anyOf(futures).get();

        executor.shutdown();
    }
}

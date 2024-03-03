package com.ecom.productsale.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
Interceptor Chain is managed by spring mvc framework, used to do some buz related works (authentication, data pre
handler, post handler) before request coming to controller layer or going out from it.

The execution order of different interceptors is decided by its adding order in configuration files like WebConfig in
 this project
 */
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RateLimit rateLimitAnnotation = handlerMethod.getMethodAnnotation(RateLimit.class);

            if (rateLimitAnnotation != null) {
                double permitsPerSecond = rateLimitAnnotation.value();
                RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);

                if (rateLimiter.tryAcquire()) {
                    return true;
                } else {
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.getWriter().write("Too many requests. Please try again later.");
                    return false;
                }

            }

        }

        return true;
    }
}
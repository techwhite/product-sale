package com.lightspeed.practicaltest.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// used to check if request exceed limit per second
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
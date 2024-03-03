package com.ecom.productsale.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/*
Aop Chain is provided by spring framework, used to do something before method execution or after
 */
@Aspect
@Component
public class DebugInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DebugInterceptor.class);

    @Pointcut("@annotation(Debug)")
    public void debugPointCut() {

    }

    @Around("debugPointCut()")
    public Object debugAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        if (isVoidMethod(joinPoint)) {
            result = null;
        }

        long interval = System.currentTimeMillis() - start;
        logger.debug("request: {}, result: {}, time cost:{}", joinPoint.getArgs(), result, interval);

        return result;
    }

    private boolean isVoidMethod(ProceedingJoinPoint joinPoint) {
        // compatible cast
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();
        Class<?> returnType= method.getReturnType();

        return returnType.equals(void.class);
    }
}

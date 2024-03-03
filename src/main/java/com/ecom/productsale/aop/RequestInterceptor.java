//package com.ecom.productsale.aop;
//
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.MissingRequestValueException;
//
//@Aspect
//@Component
//public class RequestInterceptor {
//    @Pointcut("execution(com.ecom.productsale.controller.*.*(..))")
//    public void validateAndAuthPointCut() {
//    }
//
//    @Around("validateAndAuthPointCut()")
//    public Object checkAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
//        // validate request data
//        if (isRequestValid(joinPoint)) {
//            return joinPoint.proceed();
//        } else {
//            throw new MissingRequestValueException("kdjfkd");
//        }
//
//        // get user auth information from context
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
////        // check if having permission
////        if (hasPermission(authentication)) {
////            // continue if having
////            return joinPoint.proceed();
////        } else {
////            // throw UnauthorizedAccess if not having
////            throw new AuthenticationException("Access denied") {
////
////            };
////        }
//    }
//
//    private boolean hasPermission(Authentication authentication) {
//        // 根据实际需求，检查用户是否具有指定权限
//        // 这可能涉及到访问控制列表（ACL）、角色检查等
//        // 返回 true 表示有权限，返回 false 表示没有权限
//        // 这里只是一个简单的示例
//        // 实际情况中，你可能需要根据你的认证和授权机制来实现更复杂的逻辑
//        return true;
//    }
//
//    private boolean isRequestValid(ProceedingJoinPoint joinPoint) {
//        return true;
//    }
//
//}
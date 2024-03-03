package com.ecom.productsale.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/*
    ResponseEntity don't have enough method to return more info to client. like not_found not support adding message
    So we define a customized one to return more debug information to client side. Only used in global exception
    handler
     */
@Getter
@Setter
public class ErrorResponse {
    private int code;

    private HttpStatus status;

    private String message;

    private String stackTrace;

    private Object data;

    public static ErrorResponse normal() {
        return new ErrorResponse();
    }

    public static ErrorResponse normal(HttpStatus status) {
        ErrorResponse res = new ErrorResponse();
        res.code = status.value();
        res.status = status;

        return res;
    }

    public static ErrorResponse normal(HttpStatus status, String message) {
        ErrorResponse res = normal(status);
        res.message = message;

        return res;
    }

    public static ErrorResponse normal(HttpStatus status, String message, Object data) {
        ErrorResponse res = normal(status, message);
        res.data = data;

        return res;
    }

    public static ErrorResponse fail(HttpStatus status, String message, String stackTrace) {
        ErrorResponse res = normal(status, message);
        res.stackTrace = stackTrace;

        return res;
    }


    public static ErrorResponse fail(HttpStatus status, String message, String stackTrace, Object data) {
        ErrorResponse res = normal(status, message, stackTrace);
        res.data = data;

        return res;
    }
}
package com.lightspeed.practicaltest.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

// used to return more debug information to client side
@Getter
@Setter
public class ErrorResponse {
    private int code;

    private String status;

    private String message;

    private String stackTrace;

    private Object data;

    public ErrorResponse() {
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            String message
    ) {
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            String message,
            String stackTrace
    ) {
        this(
                httpStatus,
                message
        );

        this.stackTrace = stackTrace;
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            String message,
            String stackTrace,
            Object data
    ) {
        this(
                httpStatus,
                message,
                stackTrace
        );

        this.data = data;
    }
}
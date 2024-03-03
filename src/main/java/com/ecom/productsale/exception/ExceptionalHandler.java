package com.ecom.productsale.exception;

import com.ecom.productsale.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

/*
 Global exception handling involves the centralization of exception handling logic.
 @ControllerAdvice allows the definition of global exception handlers that apply to multiple
 controllers.
 Global exception handling defines how the application should respond to different types of exceptions.  It
 includes logging the error, providing custom error messages, and returning specific HTTP status codes.

 The best practice of processing exception is
 1. use try/catch in repository layer and throw out data access exception object
 2. as most use different exception type based on buz logic in service layer, and throw them out. but should keep in
 mind that not every unexpected logic should throw exception. e.g. delete entity / findbyid / update ops but not found
 3. No needing to much handle exceptions one by one in controller layer. can use this class. but can add some
 specific handling logic in this layer, e.g. not_found since this class's http status is internal_server_error

 */
@RestControllerAdvice
public class ExceptionalHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionalHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleProductNotFoundException(EntityNotFoundException e) {
        logger.warn(e.getMessage());
        return ErrorResponse.normal(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleProductNotFoundException(UsernameNotFoundException e) {
        logger.warn(e.getMessage());
        return ErrorResponse.normal(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ErrorResponse handleProductAlreadyExistsException(DuplicateKeyException e) {
        logger.warn(e.getMessage());
        return ErrorResponse.normal(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        logger.error(stackTrace);

        // don't need to return stack to end-user
        return ErrorResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), stackTrace, e);
    }
}

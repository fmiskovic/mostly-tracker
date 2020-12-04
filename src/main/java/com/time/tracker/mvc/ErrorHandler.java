package com.time.tracker.mvc;

import com.time.tracker.errors.EntryDateException;
import com.time.tracker.mvc.responses.ApiErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest req) {
        return ApiErrorResponse.createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest req) {
        return ApiErrorResponse.createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = {NoSuchElementException.class, EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest req) {
        return ApiErrorResponse.createResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {EntityExistsException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest req) {
        return ApiErrorResponse.createResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = {EntryDateException.class})
    protected ResponseEntity<Object> handleValidationFailed(Exception ex, WebRequest req) {
        return ApiErrorResponse.createResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}

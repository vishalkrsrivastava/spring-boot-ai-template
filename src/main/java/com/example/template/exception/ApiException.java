package com.example.template.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception for all API-level errors.
 *
 * <p>Subclass this for domain-specific errors (e.g., {@link ResourceNotFoundException}).
 * {@link GlobalExceptionHandler} maps every {@code ApiException} to a JSON {@link ErrorResponse}.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public ApiException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

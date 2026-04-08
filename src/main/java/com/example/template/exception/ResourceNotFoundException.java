package com.example.template.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Thrown when a requested resource does not exist in the data store.
 * Maps to {@code 404 Not Found}.
 */
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String resourceName, UUID id) {
        super(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND",
                "%s with id '%s' not found".formatted(resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND",
                "%s with %s '%s' not found".formatted(resourceName, field, value));
    }
}

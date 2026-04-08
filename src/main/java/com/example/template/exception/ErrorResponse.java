package com.example.template.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Standard error response envelope returned for all API errors.
 *
 * <p>{@code fieldErrors} is only present for {@code 400 Bad Request} validation failures.
 */
@Schema(description = "Standard error response envelope")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        @Schema(description = "Machine-readable error code", example = "RESOURCE_NOT_FOUND")
        String errorCode,

        @Schema(description = "Human-readable error message", example = "User with id '...' not found")
        String message,

        @Schema(description = "Request path that triggered the error", example = "/api/v1/users/123")
        String path,

        @Schema(description = "ISO-8601 timestamp of the error")
        Instant timestamp,

        @Schema(description = "Field-level validation errors — present only on 400 responses")
        List<FieldError> fieldErrors
) {

    /**
     * A single field-level validation error within a {@code 400 Bad Request} response.
     */
    @Schema(description = "Single field-level validation error")
    public record FieldError(
            @Schema(example = "email") String field,
            @Schema(example = "Must be a valid email address") String message
    ) {}
}

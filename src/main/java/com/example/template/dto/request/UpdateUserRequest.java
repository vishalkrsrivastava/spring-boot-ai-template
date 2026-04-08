package com.example.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * Payload for updating an existing user.
 * All fields are optional — only provided fields are applied (patch semantics via MapStruct).
 */
@Schema(description = "Payload for updating an existing user — all fields optional")
public record UpdateUserRequest(

        @Schema(description = "First name", example = "Jane")
        @Size(min = 1, max = 100, message = "First name must be 1–100 characters")
        String firstName,

        @Schema(description = "Last name", example = "Doe")
        @Size(min = 1, max = 100, message = "Last name must be 1–100 characters")
        String lastName
) {}

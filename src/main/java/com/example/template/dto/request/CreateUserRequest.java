package com.example.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Payload for creating a new user.
 * All fields are required and validated via Bean Validation 3.0.
 */
@Schema(description = "Payload for creating a new user")
public record CreateUserRequest(

        @Schema(description = "User email address", example = "jane.doe@example.com")
        @Email(message = "Must be a valid email address")
        @NotBlank(message = "Email is required")
        String email,

        @Schema(description = "First name", example = "Jane")
        @NotBlank(message = "First name is required")
        @Size(min = 1, max = 100, message = "First name must be 1–100 characters")
        String firstName,

        @Schema(description = "Last name", example = "Doe")
        @NotBlank(message = "Last name is required")
        @Size(min = 1, max = 100, message = "Last name must be 1–100 characters")
        String lastName
) {}

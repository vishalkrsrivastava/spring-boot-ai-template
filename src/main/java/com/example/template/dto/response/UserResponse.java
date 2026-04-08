package com.example.template.dto.response;

import com.example.template.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

/**
 * Read-only representation of a {@link com.example.template.domain.entity.User}.
 */
@Schema(description = "User resource representation")
public record UserResponse(

        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Email address", example = "jane.doe@example.com")
        String email,

        @Schema(description = "First name", example = "Jane")
        String firstName,

        @Schema(description = "Last name", example = "Doe")
        String lastName,

        @Schema(description = "Account lifecycle status")
        UserStatus status,

        @Schema(description = "ISO-8601 creation timestamp")
        Instant createdAt,

        @Schema(description = "ISO-8601 last-updated timestamp")
        Instant updatedAt
) {}

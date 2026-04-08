package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 *
 * <p>Package layout:
 * <ul>
 *   <li>{@code api.v1}    — REST controllers (versioned)</li>
 *   <li>{@code config}    — Spring configuration beans</li>
 *   <li>{@code domain}    — JPA entities and domain enums</li>
 *   <li>{@code dto}       — Inbound request / outbound response records</li>
 *   <li>{@code exception} — Exception hierarchy + global handler</li>
 *   <li>{@code mapper}    — MapStruct object mappers (entity ↔ DTO)</li>
 *   <li>{@code repository}— Spring Data JPA repositories</li>
 *   <li>{@code service}   — Business-logic interfaces + implementations</li>
 * </ul>
 *
 * <p>API contract is defined first in {@code docs/api/openapi.yaml}.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

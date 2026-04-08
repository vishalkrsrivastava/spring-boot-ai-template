package com.example.template.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.1 metadata configuration.
 *
 * <ul>
 *   <li>Swagger UI  → <a href="http://localhost:8080/swagger-ui.html">swagger-ui.html</a></li>
 *   <li>JSON spec   → <a href="http://localhost:8080/v3/api-docs">v3/api-docs</a></li>
 *   <li>YAML spec   → <a href="http://localhost:8080/v3/api-docs.yaml">v3/api-docs.yaml</a></li>
 * </ul>
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.openapi.server-url:http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot AI Template API")
                        .description("REST API — API-first design with Java 21 & Spring Boot 3")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url(serverUrl).description("Default server")
                ));
    }
}

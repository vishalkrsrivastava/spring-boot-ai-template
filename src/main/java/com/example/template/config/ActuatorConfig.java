package com.example.template.config;

import org.springframework.context.annotation.Configuration;

/**
 * Actuator configuration anchor.
 *
 * <p>Endpoint exposure is controlled declaratively in {@code application.yaml}:
 * <pre>
 * management.endpoints.web.exposure.include: health,info,metrics,loggers
 * </pre>
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>{@code /actuator/health}  — liveness &amp; readiness probes</li>
 *   <li>{@code /actuator/info}    — application metadata (version, java, os)</li>
 *   <li>{@code /actuator/metrics} — Micrometer metrics</li>
 *   <li>{@code /actuator/loggers} — runtime log-level changes</li>
 * </ul>
 *
 * <p>Add custom {@code HealthIndicator} or {@code InfoContributor} beans here when needed.
 */
@Configuration
public class ActuatorConfig {
    // Endpoint exposure is controlled via application.yaml.
    // Add custom HealthIndicator / InfoContributor beans here as the project grows.
}

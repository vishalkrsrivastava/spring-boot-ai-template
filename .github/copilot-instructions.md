# GitHub Copilot Instructions

> This file is read automatically by GitHub Copilot and other AI assistants (Claude, Cursor, Windsurf).
> It provides authoritative context so suggestions stay consistent with the project's architecture.

---

## 🧠 Developer Skills

The developer persona and full engineering standards for this project live in the canonical skills file:

📄 **[`.github/skills/developer.md`](.github/skills/developer.md)**

That file defines the **Senior Principal Software Engineer** role, technical expertise (Java 21,
Spring, Spring Boot, Spring Data JPA, Spring Cloud, REST API design, SOLID/KISS/YAGNI, Design
Patterns, Architecture, Security / OWASP), and the code quality gates every change must satisfy.

---

## Project Overview

**Java 21 / Spring Boot 3.4 REST API template** following **API-first design**.

| Attribute       | Value                            |
|-----------------|----------------------------------|
| Language        | Java 21                          |
| Framework       | Spring Boot 3.4                  |
| Build           | Maven                            |
| Package root    | `com.example.template`           |
| API versioning  | URL prefix — `/api/v1/...`       |
| API contract    | `docs/api/openapi.yaml`          |
| Swagger UI      | `http://localhost:8080/swagger-ui.html` |

---

## Architecture — Request Flow

```
HTTP Request
    │
    ▼
UserController          ← api/v1/   thin layer: routing, validation, HTTP status
    │
    ▼
UserService (interface) ← service/  business rules, orchestration
    │
    ▼
UserServiceImpl         ← service/impl/
    │
    ├── UserRepository  ← repository/   Spring Data JPA
    │       │
    │       ▼
    │    User (entity)  ← domain/entity/
    │
    └── UserMapper      ← mapper/       MapStruct — entity ↔ DTO
            │
            ▼
    DTO (record)        ← dto/request|response/
```

---

## Package Map

| Package                        | Responsibility                                  |
|--------------------------------|-------------------------------------------------|
| `api.v1`                       | REST controllers — HTTP only, no business logic |
| `config`                       | Spring beans: OpenAPI, Actuator                 |
| `domain.entity`                | JPA entities (UUID PK, audit timestamps)        |
| `domain.enums`                 | Domain enumerations                             |
| `dto.request`                  | Inbound payloads — Java **records** with `@Valid`|
| `dto.response`                 | Outbound representations — Java **records**     |
| `exception`                    | `ApiException` hierarchy + `GlobalExceptionHandler` |
| `mapper`                       | MapStruct interfaces (`componentModel = "spring"`) |
| `repository`                   | `JpaRepository<Entity, UUID>` interfaces        |
| `service`                      | Business-logic **interfaces**                   |
| `service.impl`                 | Business-logic **implementations**              |

---

## Coding Conventions

### General
- Java **records** for all DTOs — immutable, no boilerplate
- Lombok `@Builder`, `@Getter`, `@Setter`, `@RequiredArgsConstructor` on entities and service impls
- `@Slf4j` for logging — never use `System.out`
- All public API methods have **Javadoc**

### Controllers (`api/v1/`)
- Annotate every class with `@Tag` and every method with `@Operation` + `@ApiResponses`
- Return type is always `ResponseEntity<T>`
- Apply `@Valid` on every `@RequestBody`
- Delegate 100% of logic to the service layer

### Services (`service/` + `service/impl/`)
- Define an **interface** in `service/`, place implementation in `service/impl/`
- `@Transactional(readOnly = true)` at class level on impls; override with `@Transactional` on writes
- Throw subclasses of `ApiException` — never throw raw `RuntimeException`

### Repositories (`repository/`)
- Extend `JpaRepository<Entity, UUID>`
- UUID primary keys only — never auto-increment integers

### Entities (`domain/entity/`)
- `@GeneratedValue(strategy = GenerationType.UUID)`
- `@CreationTimestamp` / `@UpdateTimestamp` for audit fields (`Instant` type)
- `@Enumerated(EnumType.STRING)` for all enum columns

### Mappers (`mapper/`)
- MapStruct interface, `componentModel = "spring"`
- Update methods use `NullValuePropertyMappingStrategy.IGNORE` (patch semantics)
- Never write manual field-copy code

### Exceptions (`exception/`)
- All domain errors extend `ApiException(status, errorCode, message)`
- `GlobalExceptionHandler` is the **only** place that maps exceptions to HTTP
- Error shape is always `ErrorResponse` — never return raw strings or maps

### REST naming
- Plural nouns, kebab-case: `/api/v1/users`, `/api/v1/order-items`
- Path parameters use UUID: `/{id}`

---

## Configuration Profiles

| Profile | How to activate                          | Database         |
|---------|------------------------------------------|------------------|
| `local` | default / `-Dspring.profiles.active=local` | H2 in-memory   |
| `prod`  | `SPRING_PROFILES_ACTIVE=prod`            | PostgreSQL via env vars |
| `test`  | `@ActiveProfiles("test")` in tests       | H2 in-memory     |

### Required production environment variables
```
DATASOURCE_URL        JDBC URL for the production database
DATASOURCE_USERNAME   Database username
DATASOURCE_PASSWORD   Database password
PORT                  HTTP port (default: 8080)
```

---

## Testing Strategy

| Annotation           | When to use                                   |
|----------------------|-----------------------------------------------|
| `@WebMvcTest`        | Controller slice — mock the service layer     |
| `@DataJpaTest`       | Repository slice — real H2, no web layer      |
| `@SpringBootTest`    | Full integration test — `@ActiveProfiles("test")` |

---

## How to Add a New Resource

Follow this checklist **in order**:

1. **API contract** — add paths & schemas to `docs/api/openapi.yaml`
2. **Entity** — `domain/entity/MyEntity.java` with UUID PK + audit timestamps
3. **Enum** *(if needed)* — `domain/enums/MyEntityStatus.java`
4. **Request DTO** — `dto/request/CreateMyEntityRequest.java` (record + validation)
5. **Response DTO** — `dto/response/MyEntityResponse.java` (record)
6. **Mapper** — `mapper/MyEntityMapper.java` (MapStruct interface)
7. **Repository** — `repository/MyEntityRepository.java`
8. **Service interface** — `service/MyEntityService.java`
9. **Service impl** — `service/impl/MyEntityServiceImpl.java`
10. **Controller** — `api/v1/MyEntityController.java` with full OpenAPI annotations
11. **Tests** — `@WebMvcTest` slice test in `src/test/`

---

## Key Files Quick Reference

| File                                              | Purpose                                  |
|---------------------------------------------------|------------------------------------------|
| `docs/api/openapi.yaml`                           | API contract — **source of truth**       |
| `exception/GlobalExceptionHandler.java`           | Maps all exceptions → `ErrorResponse`    |
| `exception/ErrorResponse.java`                   | Standard JSON error envelope             |
| `config/OpenApiConfig.java`                       | Swagger/OpenAPI bean + server URL        |
| `src/main/resources/application.yaml`             | Base configuration                       |
| `src/main/resources/application-local.yaml`       | Local dev overrides (H2, all actuators)  |
| `src/main/resources/application-prod.yaml`        | Production overrides                     |

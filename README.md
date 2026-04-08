# spring-boot-ai-template

A production-ready **Java 21 / Spring Boot 3.4** REST API template following **API-first design**,  
optimized for AI coding assistants — GitHub Copilot, Claude, Cursor, Windsurf.

## ✨ Features

| Feature | Detail |
|---|---|
| **Java 21** | Records, virtual-thread-ready |
| **Spring Boot 3.4** | Latest stable release |
| **API-first** | `docs/api/openapi.yaml` is the source of truth |
| **Swagger UI** | Auto-generated at `/swagger-ui.html` |
| **Actuator** | Health, info, metrics, loggers |
| **Validation** | Bean Validation 3.0 on all request DTOs |
| **MapStruct** | Compile-time type-safe entity ↔ DTO mapping |
| **Lombok** | Boilerplate reduction |
| **H2** | In-memory DB for `local` and `test` profiles |

---

## 🗂️ Project Structure

```
spring-boot-ai-template/
├── .github/
│   └── copilot-instructions.md       ← AI assistant context (read by Copilot/Claude/Cursor)
├── docs/
│   └── api/
│       └── openapi.yaml              ← API contract — source of truth
├── src/
│   ├── main/
│   │   ├── java/com/example/template/
│   │   │   ├── Application.java
│   │   │   ├── api/v1/               ← REST controllers (versioned)
│   │   │   ├── config/               ← OpenApiConfig, ActuatorConfig
│   │   │   ├── domain/
│   │   │   │   ├── entity/           ← JPA entities
│   │   │   │   └── enums/            ← Domain enumerations
│   │   │   ├── dto/
│   │   │   │   ├── request/          ← Inbound payloads (validated records)
│   │   │   │   └── response/         ← Outbound representations (records)
│   │   │   ├── exception/            ← ApiException hierarchy + GlobalExceptionHandler
│   │   │   ├── mapper/               ← MapStruct interfaces
│   │   │   ├── repository/           ← Spring Data JPA repositories
│   │   │   └── service/
│   │   │       ├── *.java            ← Business-logic interfaces
│   │   │       └── impl/             ← Implementations
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-local.yaml
│   │       └── application-prod.yaml
│   └── test/
│       ├── java/com/example/template/
│       │   ├── ApplicationTests.java
│       │   └── api/v1/UserControllerTest.java
│       └── resources/
│           └── application-test.yaml
└── pom.xml
```

---

## 🚀 Quick Start

```bash
# Run locally (H2 in-memory DB, all actuators exposed)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Build JAR
./mvnw clean package

# Run tests
./mvnw test
```

| URL | Description |
|---|---|
| `http://localhost:8080/swagger-ui.html` | Swagger UI |
| `http://localhost:8080/v3/api-docs` | OpenAPI JSON |
| `http://localhost:8080/h2-console` | H2 console (local only) |
| `http://localhost:8080/actuator` | Actuator root |

---

## 🩺 Actuator Endpoints

| Endpoint | Description |
|---|---|
| `/actuator/health` | Liveness / readiness probes |
| `/actuator/info` | App metadata (version, java, os) |
| `/actuator/metrics` | Micrometer metrics |
| `/actuator/loggers` | Runtime log-level changes |

> Local profile exposes **all** endpoints. Production exposes `health`, `info`, `metrics` only.

---

## ⚙️ Configuration Profiles

| Profile | Activation | Database |
|---|---|---|
| `local` | Default | H2 in-memory |
| `prod` | `SPRING_PROFILES_ACTIVE=prod` | PostgreSQL via env vars |
| `test` | `@ActiveProfiles("test")` | H2 in-memory |

### Production environment variables

```bash
DATASOURCE_URL=jdbc:postgresql://host:5432/mydb
DATASOURCE_USERNAME=myuser
DATASOURCE_PASSWORD=secret
PORT=8080                   # optional, default 8080
```

---

## 🧩 Adding a New Resource

Follow this checklist **in order** (mirrors `.github/copilot-instructions.md`):

1. **API contract** → add paths & schemas to `docs/api/openapi.yaml`
2. **Entity** → `domain/entity/` — UUID PK + `@CreationTimestamp`/`@UpdateTimestamp`
3. **Enum** *(if needed)* → `domain/enums/`
4. **Request DTO** → `dto/request/` (record + `@Valid` constraints)
5. **Response DTO** → `dto/response/` (record)
6. **Mapper** → `mapper/` (MapStruct interface)
7. **Repository** → `repository/` (extends `JpaRepository<Entity, UUID>`)
8. **Service interface** → `service/`
9. **Service impl** → `service/impl/`
10. **Controller** → `api/v1/` (full OpenAPI annotations)
11. **Tests** → `@WebMvcTest` slice test

---

## 📦 Tech Stack

- **Java 21** · **Spring Boot 3.4** · **Spring Data JPA** · **Hibernate 6**
- **SpringDoc OpenAPI 2** · **Lombok** · **MapStruct** · **Bean Validation 3**
- **H2** · **Spring Boot Actuator** · **Micrometer**

# ADR-0001: API-First Design with OpenAPI

## Status

Accepted

## Context

REST APIs can be designed code-first (annotations generate the spec) or API-first
(the OpenAPI spec is written first, then controllers implement it).

Code-first is faster to start, but the spec drifts as annotations change. API-first
treats the contract as a first-class artifact that clients, tests, and documentation
all derive from.

## Decision

We adopt **API-first design**:

- `docs/api/openapi.yaml` is the **single source of truth** for the API contract.
- Controllers in `api/v1/` implement this spec and carry OpenAPI annotations that
  must stay consistent with the YAML file.
- Any new endpoint must be added to `openapi.yaml` **before** writing Java code.

## Consequences

- The API contract is always up-to-date and reviewable independently of the code.
- Clients can generate SDKs from the spec without inspecting Java source.
- Developers (and AI agents) follow the "How to Add a New Resource" checklist,
  which starts with the contract.
- Minor overhead: changes require updating both the YAML and the controller annotations.

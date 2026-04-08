# ADR-0000: Use Architecture Decision Records

## Status

Accepted

## Context

We need to record the architectural decisions made on this project so that future
developers (and AI assistants) can understand **why** things are the way they are —
not just **what** the code does.

## Decision

We will use Architecture Decision Records (ADRs) as described by
[Michael Nygard](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions).

Each ADR is a short Markdown file in `docs/adr/` named `NNNN-slug.md`.

## Consequences

- Every significant architectural choice gets a permanent, discoverable record.
- New team members and AI agents can read the ADR log to understand trade-offs.
- Superseded decisions are marked as such, preserving history.

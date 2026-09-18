# certwatch

A lightweight backend service that periodically monitors domain availability and TLS certificate health, exposing the results through an HTTP API.

## Why certwatch?
Expired certificates and missing security headers are among the most common causes of preventable production outages. `certwatch` handles real-world backend concerns:
- Resilient outbound network calls with bounded timeouts.
- Time-sensitive domain models driven by testable clock abstractions (`Instant`, `Clock`).
- Scheduled checks and status persistence without premature architectural complexity.

## Getting Started

### Prerequisites
- Java 21+
- Maven Wrapper (included)

### Run the Application
```bash
./mvnw spring-boot:run
```

## Project Status

`certwatch` is under active development. The domain model — how a check result, a certificate's
reachability, and its verdict (healthy, expiring soon, expired, unreachable) are represented — is
implemented and covered by tests. The layers that turn it into a running service are being built
incrementally, one at a time:

| Layer | Responsibility | Status |
|---|---|---|
| `domain` | Check result, status, verdict — pure computation, no I/O | Done |
| `config` | `@ConfigurationProperties`, `Clock` bean | In progress |
| `web` | HTTP endpoints, request/response DTOs | Planned |
| `check` | TLS and HTTP verification | Planned |
| `persistence` | Storing check history | Planned |

## Design Choices

- **Java 21** — the LTS baseline every feature is verified against.
- **Spring Boot 4.1.0** — current stable release when the project started.
- **Sealed interfaces over a status enum.** A domain is either `Reachable(expiresAt)` or
  `Unreachable(reason)` — each state carries only the data that's actually meaningful for it,
  and the compiler enforces that every case is handled.
- **Verdict is computed, not stored.** Whether a certificate counts as "expiring soon" depends on
  a configurable threshold. Persisting the verdict itself would go stale the moment that threshold
  changes; deriving it from `status` + `now` + `warnBefore` never can.
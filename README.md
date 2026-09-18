# certwatch

A backend service for watching domains: whether a host is reachable, how long its TLS certificate
stays valid, whether the certificate chain is sound, and which security headers are missing.

*Early development — the domain model is implemented, the HTTP and TLS layers are next.*

## Why certwatch

An expired certificate takes a site down completely, and it does so on a date that was known
months in advance. The failure is entirely preventable, which is exactly why it keeps happening:
nobody watches the expiry date until the browser starts refusing the connection.

## Getting started

The build targets Java 21 and is verified against it. Maven comes with the wrapper, so no local
installation is needed.

```bash
./mvnw spring-boot:run
```

The application starts on port 8080 and serves the Actuator health endpoint:

```bash
curl -s http://localhost:8080/actuator/health
```

```json
{"groups":["liveness","readiness"],"status":"UP"}
```

To compile and run the tests:

```bash
./mvnw verify
```

## Architecture

A monolith with plain layered packages. There is one feature, so splitting it across modules or
bounded contexts would buy nothing and cost indirection.

| Package | Responsibility |
|---|---|
| `domain` | Check result, status, verdict — pure computation, no Spring and no I/O |
| `config` | `@ConfigurationProperties`, `Clock` bean |
| `web` | HTTP endpoints, request and response DTOs |
| `check` | TLS and HTTP verification, certificate chain, security headers |
| `persistence` | Storing check history |

`domain` is implemented; the rest is the layout the remaining layers are built into, one at a time.

## Design choices

- **Java 21** — the LTS baseline every feature is verified against.
- **Spring Boot 4.1.0** — the current stable release when the project started.
- **A sealed interface instead of a status enum.** A domain is either `Reachable(expiresAt)` or
  `Unreachable(reason)`, so each state carries only the data that is actually meaningful for it,
  and the compiler enforces that every case is handled.
- **The verdict is computed, not stored.** Whether a certificate counts as *expiring soon* depends
  on a configurable threshold. Persisting the verdict would make it stale the moment that threshold
  changes; deriving it from `status`, `now` and `warnBefore` never can.
- **Time is an input, not an ambient value.** Domain code never calls `Instant.now()`; the current
  instant is passed in as a parameter, so every rule can be tested at any point in time without
  waiting for the clock.
- **No `service` or `util` package.** In a layered design `service` means Spring-managed business
  beans, and a static helper class does not belong there. A `util` package turns into a bin. Pure
  functions live next to their subject in `domain`.

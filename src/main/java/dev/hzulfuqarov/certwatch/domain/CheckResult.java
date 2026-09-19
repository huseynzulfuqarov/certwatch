package dev.hzulfuqarov.certwatch.domain;

import java.time.Instant;
import java.util.Objects;

public record CheckResult(
        DomainName domain,
        Instant checkedAt,
        Status status
) {
    public CheckResult {
        Objects.requireNonNull(domain, "domain");
        Objects.requireNonNull(checkedAt, "checkedAt");
        Objects.requireNonNull(status, "status");
    }
}

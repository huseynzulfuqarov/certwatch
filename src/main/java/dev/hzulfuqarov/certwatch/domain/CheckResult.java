package dev.hzulfuqarov.certwatch.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record CheckResult(
        String domain,
        Instant checkedAt,
        List<String> missingHeaders,
        Status status
) {
    public CheckResult {
        Objects.requireNonNull(checkedAt, "checkedAt");
        missingHeaders = List.copyOf(missingHeaders);
    }
}

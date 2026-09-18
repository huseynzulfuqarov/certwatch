package dev.hzulfuqarov.certwatch.domain;

import java.util.List;

public record CheckResult(
        String domain,
        List<String> missingHeaders,
        Status status
) {
    public CheckResult {
        missingHeaders = List.copyOf(missingHeaders);
    }
}

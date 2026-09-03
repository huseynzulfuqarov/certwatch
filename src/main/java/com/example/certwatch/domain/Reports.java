package com.example.certwatch.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Reports {

    private Reports() {
    }

    public static Map<Verdict, Long> countByVerdict(List<CheckResult> results, Instant now, Duration warnBefore) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        res -> Verdict.of(res.status(), now, warnBefore),
                        () -> new EnumMap<>(Verdict.class),
                        Collectors.counting()
                ));
    }

    public static Map<String, Long> countMissingHeaders(List<CheckResult> results) {
        return results.stream()
                .flatMap(res -> res.missingHeaders().stream())
                .collect(Collectors.groupingBy(
                        header -> header,
                        Collectors.counting()
                ));
    }

    public static Optional<CheckResult> soonestExpiring(List<CheckResult> results, Instant now) {
        return results.stream()
                .filter(res -> res.status() instanceof Reachable r && r.expiresAt().isAfter(now))
                .min(Comparator.comparing(res -> expiryOrMax(res.status())));
    }

    // Unreachable results are filtered out before this runs; sorting them last
    // keeps a missing filter from ever making one of them win.
    private static Instant expiryOrMax(Status status) {
        return switch (status) {
            case Reachable r -> r.expiresAt();
            case Unreachable u -> Instant.MAX;
        };
    }
}

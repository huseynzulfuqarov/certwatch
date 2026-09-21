package dev.hzulfuqarov.certwatch.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Reports {

    private Reports() {
    }

    private static final Comparator<CheckResult> BY_EXPIRY = Comparator.comparing(res -> expiryOrMax(res.status()));

    public static Map<Verdict, Long> countByVerdict(List<CheckResult> results, Instant now, Duration warnBefore) {
        return results.stream()
                .collect(Collectors.groupingBy(
                        res -> Verdict.of(res.status(), now, warnBefore),
                        () -> new EnumMap<>(Verdict.class),
                        Collectors.counting()
                ));
    }

    public static Map<SecurityHeader, Long> countMissingHeaders(List<CheckResult> results) {
        return results.stream()
                .flatMap(Reports::missingOf)
                .collect(Collectors.groupingBy(
                        header -> header,
                        () -> new EnumMap<>(SecurityHeader.class),
                        Collectors.counting()
                ));
    }

    public static Optional<CheckResult> soonestExpiring(List<CheckResult> results, Instant now) {
        return results.stream()
                .filter(res -> res.status() instanceof Reachable r && r.expiresAt().isAfter(now))
                .min(BY_EXPIRY);
    }

    public static List<CheckResult> expired(List<CheckResult> results, Instant now) {
        return results.stream()
                .filter(res -> res.status() instanceof Reachable r && !r.expiresAt().isAfter(now))
                .sorted(BY_EXPIRY)
                .toList();
    }

    // Unreachable results are filtered out before this runs; sorting them last
    // keeps a missing filter from ever making one of them win.
    private static Instant expiryOrMax(Status status) {
        return switch (status) {
            case Reachable r -> r.expiresAt();
            case Unreachable u -> Instant.MAX;
        };
    }

    private static Stream<SecurityHeader> missingOf(CheckResult result) {
        return result.status() instanceof Reachable reachable
                ? reachable.missingHeaders().stream()
                : Stream.empty();
    }
}

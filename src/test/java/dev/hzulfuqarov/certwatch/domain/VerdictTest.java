package dev.hzulfuqarov.certwatch.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerdictTest {

    private static final Instant NOW = Instant.parse("2026-09-03T10:00:00Z");
    private static final Duration WARN = Duration.ofDays(30);

    @Test
    void expiring_exactly_now_counts_as_expired() {
        Status status = new Reachable(NOW, Set.of());
        assertEquals(Verdict.EXPIRED, Verdict.of(status, NOW, WARN));
    }

    @Test
    void expiring_exactly_at_the_threshold_is_still_healthy() {
        Status status = new Reachable(NOW.plus(WARN), Set.of());
        assertEquals(Verdict.HEALTHY, Verdict.of(status, NOW, WARN));
    }
}

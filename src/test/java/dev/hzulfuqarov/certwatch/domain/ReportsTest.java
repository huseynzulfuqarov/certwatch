package dev.hzulfuqarov.certwatch.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportsTest {

    private static final Instant NOW = Instant.parse("2026-09-03T10:00:00Z");

    @Test
    void returns_empty_when_nothing_is_reachable() {
        CheckResult down = new CheckResult(
                new DomainName("wiki.az"), NOW,
                new Unreachable(FailureKind.DNS_FAILURE, "no A record"));
        assertTrue(Reports.soonestExpiring(List.of(down), NOW).isEmpty());
    }
}
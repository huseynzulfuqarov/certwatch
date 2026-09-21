package dev.hzulfuqarov.certwatch.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReportsTest {

    private static final Instant NOW = Instant.parse("2026-09-03T10:00:00Z");

    private static CheckResult result(String domain, Instant expiresAt, SecurityHeader... missing) {
        return new CheckResult(new DomainName(domain), NOW, new Reachable(expiresAt, Set.of(missing)));
    }

    @Test
    void returns_empty_when_nothing_is_reachable() {
        CheckResult down = new CheckResult(
                new DomainName("wiki.az"), NOW,
                new Unreachable(FailureKind.DNS_FAILURE, "no A record"));
        assertTrue(Reports.soonestExpiring(List.of(down), NOW).isEmpty());
    }

    @Test
    void counts_missing_headers_in_declaration_order() {
        List<CheckResult> results = List.of(
                result("a.az", NOW.plus(Duration.ofDays(60)),
                        SecurityHeader.REFERRER_POLICY, SecurityHeader.X_FRAME_OPTIONS),
                result("b.az", NOW.plus(Duration.ofDays(40)),
                        SecurityHeader.X_CONTENT_TYPE_OPTIONS, SecurityHeader.STRICT_TRANSPORT_SECURITY),
                result("c.az", NOW.plus(Duration.ofDays(20)),
                        SecurityHeader.CONTENT_SECURITY_POLICY));

        assertEquals(
                List.of(SecurityHeader.STRICT_TRANSPORT_SECURITY,
                        SecurityHeader.CONTENT_SECURITY_POLICY,
                        SecurityHeader.X_CONTENT_TYPE_OPTIONS,
                        SecurityHeader.X_FRAME_OPTIONS,
                        SecurityHeader.REFERRER_POLICY),
                List.copyOf(Reports.countMissingHeaders(results).keySet()));
    }

    @Test
    void expired_puts_the_longest_dead_certificate_first() {
        CheckResult old = result("old.az", NOW.minus(Duration.ofDays(10)));
        CheckResult recent = result("recent.az", NOW.minus(Duration.ofDays(1)));
        CheckResult alive = result("alive.az", NOW.plus(Duration.ofDays(5)));

        assertEquals(List.of(old, recent), Reports.expired(List.of(recent, alive, old), NOW));
    }
}

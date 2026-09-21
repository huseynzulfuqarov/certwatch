package dev.hzulfuqarov.certwatch.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReachableTest {

    private static final Instant NOW = Instant.parse("2026-09-03T10:00:00Z");

    @Test
    void copies_the_header_set_so_the_caller_cannot_change_it() {
        Set<SecurityHeader> found = EnumSet.of(SecurityHeader.CONTENT_SECURITY_POLICY);
        Reachable reachable = new Reachable(NOW, found);
        found.add(SecurityHeader.REFERRER_POLICY);
        assertEquals(1, reachable.missingHeaders().size());
    }

    @Test
    void accepts_an_empty_set_of_findings() {
        assertTrue(new Reachable(NOW, Set.of()).missingHeaders().isEmpty());
    }
}
package dev.hzulfuqarov.certwatch.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public record Reachable(Instant expiresAt, Set<SecurityHeader> missingHeaders) implements Status {

    public Reachable {
        Objects.requireNonNull(expiresAt, "expiresAt");
        missingHeaders = missingHeaders.isEmpty()
                ? Set.of()
                : Collections.unmodifiableSet(EnumSet.copyOf(missingHeaders));
    }

    public Duration timeLeft(Instant now) {
        return Duration.between(now, expiresAt);
    }
}

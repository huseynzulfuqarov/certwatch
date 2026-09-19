package dev.hzulfuqarov.certwatch.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record Reachable(Instant expiresAt) implements Status {

    public Duration timeLeft(Instant now) {
        return Duration.between(now, expiresAt);
    }
}

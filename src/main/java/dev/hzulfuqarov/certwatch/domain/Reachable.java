package dev.hzulfuqarov.certwatch.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record Reachable(Instant expiresAt) implements Status {

    public long daysLeft(Instant now) {
        return ChronoUnit.DAYS.between(now, expiresAt);
    }
}

package dev.hzulfuqarov.certwatch.domain;

import java.time.Duration;
import java.time.Instant;

public enum Verdict {

    HEALTHY,
    EXPIRING_SOON,
    EXPIRED,
    UNREACHABLE;

    public static Verdict of(Status status, Instant now, Duration warnBefore) {
        return switch (status) {
            case Unreachable u -> UNREACHABLE;
            case Reachable r when !r.expiresAt().isAfter(now) -> EXPIRED;
            case Reachable r when r.expiresAt().isBefore(now.plus(warnBefore)) -> EXPIRING_SOON;
            case Reachable r -> HEALTHY;
        };
    }
}

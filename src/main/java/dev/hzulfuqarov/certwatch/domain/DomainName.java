package dev.hzulfuqarov.certwatch.domain;

import java.util.Locale;
import java.util.Objects;

public record DomainName(String value) {

    public DomainName {
        Objects.requireNonNull(value, "value");
        value = value.trim().toLowerCase(Locale.ROOT);
        if (value.isEmpty() || hasWhitespace(value)) {
            throw new IllegalArgumentException("invalid domain: " + value);
        }
    }

    private static boolean hasWhitespace(String domain) {
        return domain.chars().anyMatch(Character::isWhitespace);
    }
}

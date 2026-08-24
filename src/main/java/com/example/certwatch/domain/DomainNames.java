package com.example.certwatch.domain;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class DomainNames {

    private DomainNames() {
    }

    public static List<String> clean(List<String> rawList) {
        return rawList.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(raw -> raw.toLowerCase(Locale.ROOT))
                .filter(domain -> !domain.isEmpty())
                .filter(domain -> !hasWhitespace(domain))
                .distinct()
                .toList();
    }

    private static boolean hasWhitespace(String domain) {
        return domain.chars().anyMatch(Character::isWhitespace);
    }
}

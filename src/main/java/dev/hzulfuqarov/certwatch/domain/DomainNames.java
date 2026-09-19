package dev.hzulfuqarov.certwatch.domain;

import java.util.List;
import java.util.Objects;

public final class DomainNames {

    private DomainNames() {
    }

    public static List<DomainName> clean(List<String> rawList) {
        return rawList.stream()
                .filter(Objects::nonNull)
                .map(DomainNames::parseOrNull)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private static DomainName parseOrNull(String raw) {
        try {
            return new DomainName(raw);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

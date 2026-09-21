package dev.hzulfuqarov.certwatch.domain;

import java.util.Objects;

public record Unreachable(FailureKind kind, String details) implements Status {

    public Unreachable{
        Objects.requireNonNull(kind, "kind");
    }
}

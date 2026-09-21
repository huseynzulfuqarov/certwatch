package dev.hzulfuqarov.certwatch.domain;

public enum FailureKind {
    DNS_FAILURE,
    CONNECTION_FAILURE,
    TLS_HANDSHAKE_FAILURE,
    HTTP_ERROR,
    UNKNOWN
}

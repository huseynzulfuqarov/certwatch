package dev.hzulfuqarov.certwatch.domain;

public enum SecurityHeader {
    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security", "traffic can be downgraded to HTTP"),
    CONTENT_SECURITY_POLICY("Content-Security-Policy", "injected scripts run unrestricted"),
    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options", "the browser may guess the content type"),
    X_FRAME_OPTIONS("X-Frame-Options", "the page can be framed by another site"),
    REFERRER_POLICY("Referrer-Policy", "full URLs leak to third parties");

    private final String wireName;
    private final String risk;

    SecurityHeader(String wireName, String risk) {
        this.wireName = wireName;
        this.risk = risk;
    }

    public String wireName() {
        return wireName;
    }

    public String risk() {
        return risk;
    }
}

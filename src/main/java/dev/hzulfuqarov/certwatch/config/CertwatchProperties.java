package dev.hzulfuqarov.certwatch.config;

import dev.hzulfuqarov.certwatch.domain.DomainName;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "certwatch")
public record CertwatchProperties(
        List<DomainName> domains,
        Duration warnBefore
) {}


package dev.hzulfuqarov.certwatch.config;

import dev.hzulfuqarov.certwatch.domain.DomainName;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "certwatch")
public record CertwatchProperties(

        @NotEmpty
        List<DomainName> domains,

        @DurationUnit(ChronoUnit.DAYS)
        Duration warnBefore
) {
    public CertwatchProperties {
        if (warnBefore == null) {
            warnBefore = Duration.ofDays(30);
        }
    }
}


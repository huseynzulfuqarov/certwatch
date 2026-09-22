package dev.hzulfuqarov.certwatch.config;

import dev.hzulfuqarov.certwatch.domain.DomainName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class CertwatchPropertiesTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
            .withUserConfiguration(TestConfig.class);

    @Configuration
    @Import(DomainNameConverter.class)
    @EnableConfigurationProperties(CertwatchProperties.class)
    static class TestConfig {
    }

    @Test
    void binds_and_normalizes_a_domain_name() {
        runner.withPropertyValues("certwatch.domains[0]=  WIKI.AZ ", "certwatch.warn-before=30")
                .run(ctx -> {
                    CertwatchProperties p = ctx.getBean(CertwatchProperties.class);
                    assertThat(p.domains()).containsExactly(new DomainName("wiki.az"));
                    assertThat(p.warnBefore()).isEqualTo(Duration.ofDays(30));
                });
    }

    @Test
    void falls_back_to_thirty_days() {
        runner.withPropertyValues("certwatch.domains[0]=example.com")
                .run(ctx -> assertThat(ctx.getBean(CertwatchProperties.class).warnBefore())
                        .isEqualTo(Duration.ofDays(30)));
    }

    @Test
    void refuses_to_start_with_an_empty_domain_list() {
        runner.withPropertyValues("certwatch.domains=")
                .run(ctx -> assertThat(ctx).hasFailed());
    }
}

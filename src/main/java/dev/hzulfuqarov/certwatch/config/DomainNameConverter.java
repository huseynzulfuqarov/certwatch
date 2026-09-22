package dev.hzulfuqarov.certwatch.config;

import dev.hzulfuqarov.certwatch.domain.DomainName;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class DomainNameConverter implements Converter<String, DomainName> {

    @Override
    public DomainName convert(@NonNull String source) {
        return new DomainName(source);
    }
}

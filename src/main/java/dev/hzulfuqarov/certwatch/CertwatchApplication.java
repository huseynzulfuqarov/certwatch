package dev.hzulfuqarov.certwatch;

import dev.hzulfuqarov.certwatch.config.CertwatchProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CertwatchProperties.class)
public class CertwatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertwatchApplication.class, args);
	}

}

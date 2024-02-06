package orchestrator.config;

import orchestrator.api.properties.DiscordSetupProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({DiscordSetupProperties.class})
@PropertySource("classpath:application-api.properties")
public class ApiConfiguration {
}

package orchestrator.config;

import orchestrator.user.properties.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        JacksonConfiguration.class
})
@Configuration
@EnableConfigurationProperties({UserProperties.class})
public class AppConfiguration {

}

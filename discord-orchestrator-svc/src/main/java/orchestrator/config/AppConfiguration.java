package orchestrator.config;

import orchestrator.discord.oauth.DiscordOAuthProperties;
import orchestrator.user.property.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import({
        JacksonConfiguration.class
})
@Configuration
@EnableScheduling
@EnableConfigurationProperties({UserProperties.class, DiscordOAuthProperties.class})
public class AppConfiguration {

}

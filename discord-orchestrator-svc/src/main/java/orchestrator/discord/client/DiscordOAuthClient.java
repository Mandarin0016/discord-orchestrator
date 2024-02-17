package orchestrator.discord.client;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import orchestrator.discord.client.dto.DiscordOAuthTokenRequest;
import orchestrator.discord.client.dto.DiscordOAuthTokenResponse;
import orchestrator.discord.oauth.DiscordOAuthProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "discordOAuthClient", url = "https://discord.com/api/oauth2", configuration = {DiscordOAuthClient.FeignClientConfiguration.class})
public interface DiscordOAuthClient {

    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    DiscordOAuthTokenResponse getAuthorizationToken(@RequestBody DiscordOAuthTokenRequest discordOAuthTokenRequest);


    class FeignClientConfiguration {

        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor(DiscordOAuthProperties discordOAuthProperties) {
            return new BasicAuthRequestInterceptor(discordOAuthProperties.getClientId(), discordOAuthProperties.getClientSecret());
        }

        @Bean
        Encoder formEncoder() {
            return new feign.form.FormEncoder();
        }
    }
}

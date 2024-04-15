package orchestrator.discord.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "discord.oauth")
public class DiscordOAuthProperties {

    private String authorizationUri;
    private String redirectUri;
    private String grantTypeNormal;
    private String grantTypeRefresh;
    private String tokenType;
    private String clientId;
    private String clientSecret;

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getGrantTypeNormal() {
        return grantTypeNormal;
    }

    public String getGrantTypeRefresh() {
        return grantTypeRefresh;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}

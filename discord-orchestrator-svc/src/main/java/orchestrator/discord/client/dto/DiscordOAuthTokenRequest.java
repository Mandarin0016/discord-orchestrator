package orchestrator.discord.client.dto;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscordOAuthTokenRequest {

    @FormProperty("grant_type")
    private String grantType;
    @FormProperty("code")
    private String authorizationCode;
    @FormProperty("redirect_uri")
    private String redirectUri;
    @FormProperty("refresh_token")
    private String refreshToken;
}

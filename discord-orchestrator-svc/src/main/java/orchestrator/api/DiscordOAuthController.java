package orchestrator.api;

import orchestrator.discord.property.DiscordOAuthProperties;
import orchestrator.discord.service.DiscordOAuthService;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

import static orchestrator.api.Paths.BASE_PATH_V1;

@RestController
@RequestMapping(BASE_PATH_V1 + "/discord/oauth")
public class DiscordOAuthController {

    private final DiscordOAuthProperties discordOAuthProperties;
    private final DiscordOAuthService discordOAuthService;

    public DiscordOAuthController(DiscordOAuthProperties discordOAuthProperties, DiscordOAuthService discordOAuthService) {
        this.discordOAuthProperties = discordOAuthProperties;
        this.discordOAuthService = discordOAuthService;
    }

    @GetMapping("/authorization-uri")
    public String getAuthorizationUri() {

        return discordOAuthProperties.getAuthorizationUri();
    }

    @PostMapping("/authorize")
    @ResponseStatus(HttpStatus.CREATED)
    public void authorizeUser(@RequestHeader("discord-authorization-code") String discordAuthorizationCode,
                              @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata) throws IOException {

        discordOAuthService.authorizeUser(UUID.fromString(authenticationMetadata.getUserId()), discordAuthorizationCode, null);
    }
}

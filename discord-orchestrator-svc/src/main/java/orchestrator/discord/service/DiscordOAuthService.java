package orchestrator.discord.service;

import lombok.extern.slf4j.Slf4j;
import orchestrator.discord.client.DiscordApiClient;
import orchestrator.discord.client.DiscordOAuthClient;
import orchestrator.discord.client.dto.DiscordOAuthTokenRequest;
import orchestrator.discord.client.dto.DiscordOAuthTokenResponse;
import orchestrator.discord.client.dto.DiscordUser;
import orchestrator.discord.model.DiscordOAuth;
import orchestrator.discord.oauth.DiscordOAuthProperties;
import orchestrator.discord.repository.DiscordOAuthRepository;
import orchestrator.user.model.User;
import orchestrator.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Slf4j
public class DiscordOAuthService {

    private final DiscordOAuthProperties oAuthProperties;
    private final DiscordOAuthClient oAuthClient;
    private final DiscordApiClient apiClient;
    private final DiscordOAuthRepository oAuthRepository;
    private final UserService userService;

    public DiscordOAuthService(DiscordOAuthProperties oAuthProperties, DiscordOAuthClient oAuthClient, DiscordApiClient apiClient, DiscordOAuthRepository oAuthRepository, UserService userService) {
        this.oAuthProperties = oAuthProperties;
        this.oAuthClient = oAuthClient;
        this.apiClient = apiClient;
        this.oAuthRepository = oAuthRepository;
        this.userService = userService;
    }

    @Transactional
    public void authorizeUser(UUID userId, String discordAuthorizationCode, String refreshToken) {

        DiscordOAuthTokenRequest oAuthTokenRequest = mapToDiscordAuthorizationRequest(discordAuthorizationCode, refreshToken);
        User user = userService.getById(userId, true);

        log.info("Initiating discord authorization process for User with id=[%s].".formatted(user.getId()));

        try {
            DiscordOAuthTokenResponse oAuthTokenResponse = oAuthClient.getAuthorizationToken(oAuthTokenRequest);
            upsertDiscordOAuth(userId, oAuthTokenResponse, discordAuthorizationCode != null);
            log.info("Discord authorization process successfully finished for User with id=[%s].".formatted(user.getId()));
        } catch (Exception e) {
            //TODO: Unauthorize user if any exception thrown, think of any case.
        }
    }

    private void upsertDiscordOAuth(UUID userId, DiscordOAuthTokenResponse oAuthTokenResponse, boolean isManualUserAuthorizationTriggered) {

        DiscordOAuth discordOAuth = oAuthRepository.findByUserId(userId).orElse(null);
        DiscordOAuth newDiscordOAuth = mapToDiscordOAuth(oAuthTokenResponse);

        if (discordOAuth != null) {
            newDiscordOAuth.setId(discordOAuth.getId());
        }

        if (discordOAuth == null || isManualUserAuthorizationTriggered) {
            String authHeader = "%s %s".formatted(oAuthTokenResponse.getTokenType(), oAuthTokenResponse.getAccessToken());
            DiscordUser discordUserInfo = apiClient.getUserDetails(authHeader);
            newDiscordOAuth.setDiscordUserId(discordUserInfo.getId());
            newDiscordOAuth.setUserId(userId);
            userService.updateUserDiscordId(userId, discordUserInfo.getId());
        }

        userService.updateUserDiscordAuthorizationStatus(userId, true);
        oAuthRepository.save(newDiscordOAuth);
    }

    private DiscordOAuth mapToDiscordOAuth(DiscordOAuthTokenResponse oAuthTokenResponse) {

        return DiscordOAuth.builder()
                .tokenType(oAuthTokenResponse.getTokenType())
                .accessToken(oAuthTokenResponse.getAccessToken())
                .refreshToken(oAuthTokenResponse.getRefreshToken())
                .scopes(oAuthTokenResponse.getScope())
                .expireAt(OffsetDateTime.now().plusSeconds(oAuthTokenResponse.getExpiresIn()))
                .build();
    }

    private DiscordOAuthTokenRequest mapToDiscordAuthorizationRequest(String discordAuthorizationCode, String refreshToken) {

        String grantType = refreshToken != null ? oAuthProperties.getGrantTypeRefresh() : oAuthProperties.getGrantTypeNormal();

        return DiscordOAuthTokenRequest.builder()
                .grantType(grantType)
                .authorizationCode(discordAuthorizationCode)
                .redirectUri(oAuthProperties.getRedirectUri())
                .refreshToken(refreshToken)
                .build();
    }

}

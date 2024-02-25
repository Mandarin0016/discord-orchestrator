package orchestrator.discord.service;

import orchestrator.discord.client.DiscordApiClient;
import orchestrator.discord.client.DiscordOAuthClient;
import orchestrator.discord.client.dto.DiscordOAuthTokenRequest;
import orchestrator.discord.model.DiscordOAuth;
import orchestrator.discord.oauth.DiscordOAuthProperties;
import orchestrator.discord.repository.DiscordOAuthRepository;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscordOAuthServiceUTest {

    @Mock
    private DiscordOAuthProperties oAuthProperties;
    @Mock
    private DiscordOAuthClient oAuthClient;
    @Mock
    private DiscordApiClient apiClient;
    @Mock
    private DiscordOAuthRepository oAuthRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private DiscordOAuthService oAuthService;


    @Test
    void deAuthorizeUser_happyPath() {

        // given
        UUID userId = UUID.randomUUID();

        // when
        oAuthService.deAuthorizeUser(userId);

        // then
        verify(userService, times(1))
                .updateUserDiscordId(userId, null);
        verify(userService, times(1))
                .updateUserDiscordAuthorizationStatus(userId, false);
        verify(oAuthRepository, times(1))
                .deleteByUserId(userId);
    }

    @Test
    void deAuthorizeUser_missingUserWithGivenId_throwsException() {

        // given
        UUID userId = UUID.randomUUID();
        doThrow(UserDomainException.class)
                .when(userService)
                .updateUserDiscordId(userId, null);

        // when
        assertThrows(UserDomainException.class, () -> oAuthService.deAuthorizeUser(userId));

        // then
        verify(userService, never()).updateUserDiscordAuthorizationStatus(userId, false);
        verify(oAuthRepository, never()).deleteByUserId(userId);
    }

    @Test
    void authorizeUser_HappyPath() {

        // given
        UUID userId = UUID.randomUUID();
        String discordAuthorizationCode = UUID.randomUUID().toString();
        String refreshToken = "refreshToken";
        DiscordOAuthTokenRequest oAuthTokenRequest = new DiscordOAuthTokenRequest("grantType", refreshToken, discordAuthorizationCode, "Url");
        DiscordOAuth discordOAuth = new DiscordOAuth();

        // when
        oAuthService.authorizeUser(userId, discordAuthorizationCode, refreshToken);

        // then
        verify(userService, times(1))
                .getById(userId, true);
        verify(oAuthClient, times(1))
                .authorizeUser(oAuthTokenRequest);
        verify(oAuthRepository, times(1))
                .findByUserId(userId);
    }

}

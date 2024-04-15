package orchestrator.discord.service;

import orchestrator.discord.client.DiscordApiClient;
import orchestrator.discord.client.DiscordOAuthClient;
import orchestrator.discord.client.dto.DiscordOAuthTokenRequest;
import orchestrator.discord.client.dto.DiscordOAuthTokenResponse;
import orchestrator.discord.client.dto.DiscordUser;
import orchestrator.discord.model.DiscordOAuth;
import orchestrator.discord.property.DiscordOAuthProperties;
import orchestrator.discord.repository.DiscordOAuthRepository;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.model.User;
import orchestrator.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static orchestrator.TestBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Nested
    @DisplayName("authorizeUser method")
    class AuthorizeUser {
        private static final String NORMAL_GRANT_TYPE = "normal";
        private static final String REFRESH_GRANT_TYPE = "refresh";
        private static final String REDIRECT_URI = "www.discord-orchestrator.com/redirect";
        @Captor
        private ArgumentCaptor<DiscordOAuth> discordOAuthArgumentCaptor;

        @Test
        void authorizeUser_withDiscordAuthorizationCode_happyPath() {

            // given
            UUID userId = UUID.randomUUID();
            String discordAuthorizationCode = UUID.randomUUID().toString();
            User user = aRandomUserBuilder()
                    .id(userId)
                    .discordId(null)
                    .isDiscordAuthorized(false)
                    .build();
            when(userService.getById(userId, true)).thenReturn(user);

            DiscordOAuthTokenResponse oAuthTokenResponse = aRandomDiscordOAuthTokenResponseBuilder().build();
            when(oAuthClient.authorizeUser(any())).thenReturn(oAuthTokenResponse);

            String discordUserId = "1234";
            DiscordUser discordUser = aRandomDiscordUser(discordUserId, "Mandarin");
            when(apiClient.getUserDetails(any())).thenReturn(discordUser);

            when(oAuthProperties.getGrantTypeNormal()).thenReturn(NORMAL_GRANT_TYPE);
            when(oAuthProperties.getRedirectUri()).thenReturn(REDIRECT_URI);
            when(oAuthRepository.findByUserId(userId)).thenReturn(Optional.empty());

            // when
            oAuthService.authorizeUser(userId, discordAuthorizationCode, null);

            // then
            verify(oAuthClient, times(1)).authorizeUser(any(DiscordOAuthTokenRequest.class));
            verify(apiClient, times(1)).getUserDetails(anyString());
            verify(oAuthRepository, times(1)).findByUserId(userId);
            verify(userService, times(1)).getById(userId, true);
            verify(userService, times(1)).updateUserDiscordId(userId, discordUserId);
            verify(userService, times(1)).updateUserDiscordAuthorizationStatus(userId, true);
            verify(oAuthRepository, times(1)).save(discordOAuthArgumentCaptor.capture());

            DiscordOAuth discordOAuth = discordOAuthArgumentCaptor.getValue();
            assertNull(discordOAuth.getId());
            assertThat(discordOAuth.getUserId(), is(userId));
            assertThat(discordOAuth.getDiscordUserId(), is(discordUserId));
            assertThat(discordOAuth.getTokenType(), is(oAuthTokenResponse.getTokenType()));
            assertThat(discordOAuth.getAccessToken(), is(oAuthTokenResponse.getAccessToken()));
            assertThat(discordOAuth.getRefreshToken(), is(oAuthTokenResponse.getRefreshToken()));
            assertThat(discordOAuth.getExpireAt(), greaterThan(OffsetDateTime.now()));
            assertThat(discordOAuth.getScopes(), is(oAuthTokenResponse.getScopes()));
        }

        @Test
        void authorizeUser_withRefreshToken_happyPath() {

            // given
            UUID userId = UUID.randomUUID();
            String discordUserId = "1234";
            String refreshToken = UUID.randomUUID().toString();
            User user = aRandomUserBuilder()
                    .id(userId)
                    .discordId(discordUserId)
                    .isDiscordAuthorized(true)
                    .build();
            when(userService.getById(userId, true)).thenReturn(user);

            DiscordOAuthTokenResponse oAuthTokenResponse = aRandomDiscordOAuthTokenResponseBuilder().build();
            when(oAuthClient.authorizeUser(any())).thenReturn(oAuthTokenResponse);

            when(oAuthProperties.getGrantTypeRefresh()).thenReturn(REFRESH_GRANT_TYPE);
            when(oAuthProperties.getRedirectUri()).thenReturn(REDIRECT_URI);

            DiscordOAuth existingDiscordAuthorization = aRandomDiscordOAuthBuilder(userId, discordUserId).build();
            when(oAuthRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(existingDiscordAuthorization));

            // when
            oAuthService.authorizeUser(userId, null, refreshToken);

            // then
            verify(userService, times(1)).getById(userId, true);
            verify(oAuthClient, times(1)).authorizeUser(any(DiscordOAuthTokenRequest.class));
            verify(oAuthRepository, times(1)).findByUserId(userId);
            verify(apiClient, times(0)).getUserDetails(anyString());
            verify(userService, times(0)).updateUserDiscordId(any(), any());
            verify(userService, times(0)).updateUserDiscordAuthorizationStatus(any(), anyBoolean());
            verify(oAuthRepository, times(1)).save(discordOAuthArgumentCaptor.capture());

            DiscordOAuth discordOAuth = discordOAuthArgumentCaptor.getValue();
            assertThat(discordOAuth.getId(), is(existingDiscordAuthorization.getId()));
            assertThat(discordOAuth.getUserId(), is(existingDiscordAuthorization.getUserId()));
            assertThat(discordOAuth.getDiscordUserId(), is(existingDiscordAuthorization.getDiscordUserId()));
            assertThat(discordOAuth.getTokenType(), is(oAuthTokenResponse.getTokenType()));
            assertThat(discordOAuth.getAccessToken(), is(oAuthTokenResponse.getAccessToken()));
            assertThat(discordOAuth.getRefreshToken(), is(oAuthTokenResponse.getRefreshToken()));
            assertThat(discordOAuth.getExpireAt(), greaterThan(OffsetDateTime.now()));
            assertThat(discordOAuth.getScopes(), is(oAuthTokenResponse.getScopes()));
        }
    }

    @Nested
    @DisplayName("deAuthorizeUser method")
    class DeAuthorizeUser {

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
    }
}

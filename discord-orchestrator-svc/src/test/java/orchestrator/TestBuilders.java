package orchestrator;

import orchestrator.discord.client.dto.DiscordOAuthTokenResponse;
import orchestrator.discord.client.dto.DiscordUser;
import orchestrator.discord.model.DiscordOAuth;
import orchestrator.user.model.SystemAuthority;
import orchestrator.user.model.UserRole;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.command.output.UserProfileOutput.UserProfileOutputBuilder;
import orchestrator.user.model.User;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public final class TestBuilders {

    public static UserRegisterInput.UserRegisterInputBuilder aRandomUserRegisterInputBuilder() {

        return UserRegisterInput.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .username("ivan.ivanov")
                .email("ivan.ivanov@gtest.com")
                .password("J1o4r2c3k1!");
    }

    public static User.UserBuilder aRandomUserBuilder() {

        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Ivan")
                .lastName("Ivanov")
                .username("ivan.ivanov")
                .email("ivan.ivanov@gtest.com")
                .password("J1o4r2c3k1!")
                .authorities(Set.of(SystemAuthority.CREATE_DISCORD_SERVER_SETUP_REQUEST))
                .role(UserRole.USER)
                .createOn(OffsetDateTime.now())
                .updatedOn(OffsetDateTime.now())
                .isActive(true);
    }

    public static UserLoginInput.UserLoginInputBuilder aRadonUserLoginInputBuilder() {

        return UserLoginInput.builder()
                .usernameOrEmail("ivan.ivan")
                .password("J1o4r2c3k1!");
    }

    public static UserProfileOutputBuilder aRandomProfileOutputBuilder() {

        return UserProfileOutput.builder()
                .id(UUID.randomUUID())
                .firstName("Ivan")
                .lastName("Ivanov")
                .username("ivan.ivanov")
                .email("ivan.ivanov@gtest.com")
                .isActive(true)
                .authorities(Set.of(SystemAuthority.CREATE_DISCORD_SERVER_SETUP_REQUEST))
                .role(UserRole.USER)
                .createOn(OffsetDateTime.now())
                .updatedOn(OffsetDateTime.now());
    }

    public static DiscordOAuthTokenResponse.DiscordOAuthTokenResponseBuilder aRandomDiscordOAuthTokenResponseBuilder() {

        return DiscordOAuthTokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .scopes("identity")
                .expiresIn((int) Duration.ofDays(7).toSeconds());
    }

    public static DiscordOAuth.DiscordOAuthBuilder aRandomDiscordOAuthBuilder(UUID userId, String discordUserId) {

        return DiscordOAuth.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .discordUserId(discordUserId)
                .tokenType("Bearer")
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .scopes("identity")
                .expireAt(OffsetDateTime.now().plusDays(7));
    }

    public static DiscordUser aRandomDiscordUser(String userId, String username) {

        return DiscordUser.builder()
                .id(userId)
                .username(username)
                .build();
    }
}

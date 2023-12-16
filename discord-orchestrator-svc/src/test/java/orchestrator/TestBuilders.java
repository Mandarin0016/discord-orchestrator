package orchestrator;

import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.model.User;

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
                .authorities(Set.of(UserAuthority.CREATE_DISCORD_SERVER_SETUP_REQUEST))
                .role(UserRole.USER)
                .createOn(OffsetDateTime.now())
                .updatedOn(OffsetDateTime.now())
                .isActive(true);
    }

    public static UserLoginInput.UserLoginInputBuilder aRadonUserLoginInputBuilder() {

        return  UserLoginInput.builder()
                .usernameOrEmail("ivan.ivan")
                .password("J1o4r2c3k1!");
    }

}

package orchestrator.user.service;

import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.model.User;
import orchestrator.user.properties.UserProperties;
import orchestrator.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static orchestrator.TestBuilders.aRandomUserBuilder;
import static orchestrator.TestBuilders.aRandomUserRegisterInputBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProperties userProperties;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("Tests for method register()")
    class Register {
        @Test
        public void givenValidRegisterInput_whenRegister_thenUserSuccessfullySaved() {

            // given
            UserRegisterInput registerInput = aRandomUserRegisterInputBuilder().build();
            boolean accountState = true;
            boolean accountAlreadyExist = false;
            UserRole accountRole = UserRole.USER;
            Set<UserAuthority> accountAuthorities = Set.of(UserAuthority.CREATE_DISCORD_SERVER_SETUP_REQUEST);

            User savedUser = aRandomUserBuilder()
                    .firstName(registerInput.getFirstName())
                    .lastName(registerInput.getLastName())
                    .email(registerInput.getEmail())
                    .username(registerInput.getUsername())
                    .authorities(accountAuthorities)
                    .role(accountRole)
                    .isActive(accountState)
                    .build();

            when(userProperties.getDefaultAccountState())
                    .thenReturn(accountState);
            when(userProperties.getDefaultRole())
                    .thenReturn(accountRole);
            when(userProperties.getDefaultAuthorities())
                    .thenReturn(accountAuthorities);
            when(userRepository.existsByUsernameOrEmail(registerInput.getUsername(), registerInput.getEmail()))
                    .thenReturn(accountAlreadyExist);
            when(userRepository.save(any(User.class)))
                    .thenReturn(savedUser);


            // when
            userService.register(registerInput);

            // then
            verify(userRepository, times(1))
                    .existsByUsernameOrEmail(registerInput.getUsername(), registerInput.getEmail());
            verify(userRepository, times(1))
                    .save(userArgumentCaptor.capture());
            verify(userProperties, times(1))
                    .getDefaultAccountState();
            verify(userProperties, times(1))
                    .getDefaultRole();
            verify(userProperties, times(1))
                    .getDefaultAuthorities();

            User userToBeSaved = userArgumentCaptor.getValue();
            assertThat(userToBeSaved.getId(), isNull());
            assertThat(userToBeSaved.getFirstName(), is(registerInput.getFirstName()));
            assertThat(userToBeSaved.getLastName(), is(registerInput.getLastName()));
            assertThat(userToBeSaved.getEmail(), is(registerInput.getEmail()));
            assertThat(userToBeSaved.getUsername(), is(registerInput.getUsername()));
            assertThat(userToBeSaved.getPassword(), not(registerInput.getPassword()));
            assertThat(userToBeSaved.isActive(), is(accountState));
            assertThat(userToBeSaved.getCreateOn(), isNull());
            assertThat(userToBeSaved.getUpdatedOn(), isNull());
            assertThat(userToBeSaved.getRole(), is(accountRole));
            assertThat(userToBeSaved.getAuthorities(), hasSize(1));
            assertThat(userToBeSaved.getAuthorities(), is(accountAuthorities));
        }

        @Test
        public void givenExistingEmail_whenRegister_thenThrowUserDomainException() {

        }

        @Test
        public void givenExistingUsername_whenRegister_thenThrowUserDomainException() {

        }
    }
}

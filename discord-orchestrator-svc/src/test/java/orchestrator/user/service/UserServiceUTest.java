package orchestrator.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.exception.UserDomainException;
import orchestrator.user.model.User;
import orchestrator.user.properties.UserProperties;
import orchestrator.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static orchestrator.TestBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserProperties userProperties;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("Tests for method register()")
    class Register {

        @Test
        void givenValidRegisterInput_whenRegister_thenUserSuccessfullySaved() {

            // given
            UserRegisterInput registerInput = aRandomUserRegisterInputBuilder().build();
            boolean accountState = true;
            boolean accountAlreadyExist = false;
            UserRole accountRole = UserRole.USER;
            String hashedPassword = "hashedPassword";
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
            when(passwordEncoder.getHashBase64(registerInput.getPassword()))
                    .thenReturn(hashedPassword);

            // when
            UserProfileOutput registerOutput = userService.register(registerInput);

            // then
            verify(userRepository, times(1))
                    .existsByUsernameOrEmail(registerInput.getUsername(), registerInput.getEmail());
            verify(userProperties, times(1))
                    .getDefaultAccountState();
            verify(userProperties, times(1))
                    .getDefaultRole();
            verify(userProperties, times(1))
                    .getDefaultAuthorities();
            verify(userRepository).save(userArgumentCaptor.capture());

            User userToBeSaved = userArgumentCaptor.getValue();
            assertThat(userToBeSaved.getId(), is(nullValue()));
            assertThat(userToBeSaved.getFirstName(), is(registerInput.getFirstName()));
            assertThat(userToBeSaved.getLastName(), is(registerInput.getLastName()));
            assertThat(userToBeSaved.getEmail(), is(registerInput.getEmail()));
            assertThat(userToBeSaved.getUsername(), is(registerInput.getUsername()));
            assertThat(userToBeSaved.getPassword(), is(hashedPassword));
            assertThat(userToBeSaved.isActive(), is(accountState));
            assertThat(userToBeSaved.getCreateOn(), is(nullValue()));
            assertThat(userToBeSaved.getUpdatedOn(), is(nullValue()));
            assertThat(userToBeSaved.getRole(), is(accountRole));
            assertThat(userToBeSaved.getAuthorities(), hasSize(1));
            assertThat(userToBeSaved.getAuthorities(), is(accountAuthorities));
            assertCorrectProfileOutput(registerOutput, savedUser);
        }

        @Test
        void givenExistingEmailOrUsername_whenRegister_thenThrowUserDomainException() {

            // given
            UserRegisterInput registerInput = aRandomUserRegisterInputBuilder().build();
            when(userRepository.existsByUsernameOrEmail(any(), any()))
                    .thenReturn(true);

            // when & then
            assertThrows(UserDomainException.class, () -> userService.register(registerInput));
            verifyNoInteractions(userProperties);
            verify(userRepository, times(1))
                    .existsByUsernameOrEmail(any(), any());
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Nested
    @DisplayName("Tests for method login()")
    class Login {

        @Test
        void givenValidLoginInput_whenLogin_thenUserSuccessfullyLogin() {

            // given
            UserLoginInput loginInput = aRadonUserLoginInputBuilder().build();
            boolean accountState = true;
            User retrievedUser = aRandomUserBuilder()
                    .username(loginInput.getUsernameOrEmail())
                    .password(loginInput.getPassword())
                    .isActive(accountState)
                    .build();
            when(userRepository.findByUsernameOrEmail(loginInput.getUsernameOrEmail(),loginInput.getUsernameOrEmail()))
                    .thenReturn(Optional.of(retrievedUser));
            when(passwordEncoder.getHashBase64(loginInput.getPassword()))
                    .thenReturn(retrievedUser.getPassword());

            // when
            UserProfileOutput loginOutput = userService.login(loginInput);

            // then
            verify(userRepository, times(1))
                    .findByUsernameOrEmail(loginInput.getUsernameOrEmail(), loginInput.getUsernameOrEmail());
            verify(passwordEncoder, times(1))
                    .getHashBase64(loginInput.getPassword());
            assertCorrectProfileOutput(loginOutput, retrievedUser);
        }
    }

    private void assertCorrectProfileOutput(UserProfileOutput profileOutput, User user) {

        assertThat(profileOutput.getId(), is(user.getId()));
        assertThat(profileOutput.getFirstName(), is(user.getFirstName()));
        assertThat(profileOutput.getLastName(), is(user.getLastName()));
        assertThat(profileOutput.getUsername(), is(user.getUsername()));
        assertThat(profileOutput.getEmail(), is(user.getEmail()));
        assertThat(profileOutput.getRole(), is(user.getRole()));
        assertThat(profileOutput.getAuthorities(), is(user.getAuthorities()));
        assertThat(profileOutput.getCreateOn(), is(user.getCreateOn()));
        assertThat(profileOutput.getUpdatedOn(), is(user.getUpdatedOn()));
    }
}
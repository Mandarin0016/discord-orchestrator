package orchestrator.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;
import orchestrator.user.command.input.UserRegisterInput;
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

import java.util.Set;

import static orchestrator.TestBuilders.aRandomUserBuilder;
import static orchestrator.TestBuilders.aRandomUserRegisterInputBuilder;
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
            assertThat(userToBeSaved.getPassword(), not(registerInput.getPassword()));
            assertThat(userToBeSaved.isActive(), is(accountState));
            assertThat(userToBeSaved.getCreateOn(), is(nullValue()));
            assertThat(userToBeSaved.getUpdatedOn(), is(nullValue()));
            assertThat(userToBeSaved.getRole(), is(accountRole));
            assertThat(userToBeSaved.getAuthorities(), hasSize(1));
            assertThat(userToBeSaved.getAuthorities(), is(accountAuthorities));
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

        @Test
        void givenInvalidMessageDigestAlgorithm_whenRegister_thenThrowUserDomainException() {

            // given
            UserRegisterInput registerInput = aRandomUserRegisterInputBuilder().build();
            when(userRepository.existsByUsernameOrEmail(any(), any()))
                    .thenReturn(false);
            try (MockedStatic<MessageDigest> mocked = mockStatic(MessageDigest.class)) {

                mocked.when(() -> MessageDigest.getInstance(any())).thenThrow(NoSuchAlgorithmException.class);

                // when & then
                assertThrows(UserDomainException.class, () -> userService.register(registerInput));
                verifyNoInteractions(userProperties);
                verify(userRepository, times(1))
                        .existsByUsernameOrEmail(any(), any());
                verifyNoMoreInteractions(userRepository);
            }
        }
    }
}

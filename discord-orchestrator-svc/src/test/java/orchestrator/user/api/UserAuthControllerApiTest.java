package orchestrator.user.api;

import java.util.stream.Stream;
import orchestrator.api.MediaType;
import orchestrator.api.UserAuthController;
import orchestrator.api.dto.user.UserLogin;
import orchestrator.security.jwt.auth.AuthenticationMetadata.Builder;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.command.output.UserProfileOutput.UserProfileOutputBuilder;
import orchestrator.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static orchestrator.TestBuilders.aRandomProfileOutput;
import static orchestrator.api.ExceptionAdvice.BAD_REQUEST_ERROR_UUID;
import static orchestrator.api.MediaType.USER_LOGIN_RESPONSE;
import static orchestrator.api.Paths.BASE_PATH_V1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserAuthController.class)
class UserAuthControllerApiTest extends ApiTestBase {

    private static final String BASE_PATH = BASE_PATH_V1 + "/auth";
    private static final String REGISTER = BASE_PATH + "/register";
    private static final String LOGIN = BASE_PATH + "/login";

    @MockBean
    private UserService userService;

    @Test
    void login_returnsOk_whenHappyPath() throws Exception {

        // given
        UserLogin userLogin = UserLogin.builder()
                .usernameOrEmail("ivan-ivanov")
                .password("test12341234")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOGIN)
                .contentType(MediaType.USER_LOGIN_REQUEST)
                .content(objectMapper.writeValueAsString(userLogin));
        when(userService.login(any(UserLoginInput.class)))
                .thenReturn(aRandomProfileOutput().build());

        // when & then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentType(USER_LOGIN_RESPONSE))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .exists(HTTP_AUTHORIZATION_HEADER))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("authorization")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.userId")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.email")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.username")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.isActive")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.role")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("data.authorities")
                        .isNotEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidUserLoginParameters")
    void login_returnsBadRequest_whenInvalidDataGiven(UserLogin userLogin) throws Exception {

        // given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOGIN)
                .contentType(MediaType.USER_LOGIN_REQUEST)
                .content(objectMapper.writeValueAsString(userLogin));

        // when & then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .doesNotExist(HTTP_AUTHORIZATION_HEADER))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("errorCode")
                        .value(BAD_REQUEST_ERROR_UUID.toString()));
    }

    @Test
    void login_returnsBadRequest_whenInvalidContentType() throws Exception {

        // given
        UserLogin userLogin = UserLogin.builder()
                .usernameOrEmail("ivan-ivanov")
                .password("test12341234")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOGIN)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin));

        // when & then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .header()
                        .doesNotExist(HTTP_AUTHORIZATION_HEADER))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("errorCode")
                        .value(BAD_REQUEST_ERROR_UUID.toString()));
    }

    public static Stream<Arguments> invalidUserLoginParameters() {

        UserLogin userLoginWithMissingEmailOrUsername = UserLogin.builder()
                .password("Jk1124!@ssd")
                .build();
        UserLogin userLoginWithMissingPassword = UserLogin.builder()
                .usernameOrEmail("ivan.ivanov")
                .build();
        UserLogin userLoginWithMissingBothPasswordAndEmailOrUsername = UserLogin.builder()
                .build();

        return Stream.of(
                Arguments.of(userLoginWithMissingEmailOrUsername),
                Arguments.of(userLoginWithMissingPassword),
                Arguments.of(userLoginWithMissingBothPasswordAndEmailOrUsername)
        );
    }

}

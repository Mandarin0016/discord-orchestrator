package orchestrator.api;

import orchestrator.api.dto.user.UserLogin;
import orchestrator.api.dto.user.UserRegister;
import orchestrator.api.mapper.DtoMapper;
import orchestrator.security.AuthorizationGenerator;
import orchestrator.security.dto.UserAuthentication;
import orchestrator.security.dto.UserAuthenticationDetails;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static orchestrator.api.MediaType.USER_LOGIN_REQUEST;
import static orchestrator.api.MediaType.USER_LOGIN_RESPONSE;
import static orchestrator.api.MediaType.USER_REGISTRATION_REQUEST;
import static orchestrator.api.MediaType.USER_REGISTRATION_RESPONSE;
import static orchestrator.api.Paths.BASE_PATH_V1;
import static orchestrator.api.mapper.DtoMapper.mapToUserAuthenticationDetails;

@RestController
@RequestMapping(BASE_PATH_V1 + "/auth")
public class UserAuthController {

    private final UserService userService;
    private final AuthorizationGenerator authorizationGenerator;

    public UserAuthController(UserService userService, AuthorizationGenerator authorizationGenerator) {

        this.userService = userService;
        this.authorizationGenerator = authorizationGenerator;
    }

    @PostMapping(value = "/login", consumes = USER_LOGIN_REQUEST, produces = USER_LOGIN_RESPONSE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAuthentication> login(@Validated @RequestBody UserLogin login) {

        UserLoginInput userLoginInputCommand = DtoMapper.mapToLoginInput(login);
        UserProfileOutput userProfileOutputCommand = userService.login(userLoginInputCommand);

        UserAuthenticationDetails userAuthenticationDetails = mapToUserAuthenticationDetails(userProfileOutputCommand);
        String authorizationHeader = authorizationGenerator.generateAuthorizationHeader(userAuthenticationDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .body(UserAuthentication.builder()
                        .authorization(authorizationHeader)
                        .data(userAuthenticationDetails)
                        .build());
    }

    @PostMapping(value = "/register", consumes = USER_REGISTRATION_REQUEST, produces = USER_REGISTRATION_RESPONSE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserAuthentication> register(@Validated @RequestBody UserRegister register) {

        UserRegisterInput userRegisterInputCommand = DtoMapper.mapToRegisterInput(register);
        UserProfileOutput userProfileOutputCommand = userService.register(userRegisterInputCommand);

        UserAuthenticationDetails userAuthenticationDetails = mapToUserAuthenticationDetails(userProfileOutputCommand);
        String authorizationHeader = authorizationGenerator.generateAuthorizationHeader(userAuthenticationDetails);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .body(UserAuthentication.builder()
                        .authorization(authorizationHeader)
                        .data(userAuthenticationDetails)
                        .build());
    }
}

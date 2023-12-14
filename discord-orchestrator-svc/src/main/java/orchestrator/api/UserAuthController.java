package orchestrator.api;

import java.util.UUID;
import orchestrator.api.dto.user.UserRegister;
import orchestrator.security.dto.UserAuthenticatedResponse;
import orchestrator.security.jwt.AuthenticationJwtDecoder;
import orchestrator.security.jwt.AuthenticationJwtEncoder;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.user.command.UserRegisterInput;
import orchestrator.user.command.UserRegisterOutput;
import orchestrator.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static orchestrator.api.MediaType.USER_REGISTRATION_REQUEST;
import static orchestrator.api.MediaType.USER_REGISTRATION_RESPONSE;
import static orchestrator.api.Paths.BASE_PATH_V1;
import static orchestrator.api.mapper.DtoMapper.mapToServerSetupResponse;

@RestController
@RequestMapping(BASE_PATH_V1)
public class UserAuthController {

    private final UserService userService;

    private final AuthenticationJwtDecoder decoder;
    private final AuthenticationJwtEncoder encoder;

    public UserAuthController(UserService userService, AuthenticationJwtDecoder decoder,
            AuthenticationJwtEncoder encoder) {

        this.userService = userService;

        this.decoder = decoder;
        this.encoder = encoder;
    }


    @PostMapping("/login")
    public ResponseEntity<UserAuthenticatedResponse> login(
            @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata) {

        System.out.println();
        return null;
    }

    @PostMapping(value = "/register", consumes = USER_REGISTRATION_REQUEST, produces = USER_REGISTRATION_RESPONSE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserAuthenticatedResponse> register(@Validated @RequestBody UserRegister register) {

        UserRegisterInput userRegisterInputCommand = mapToServerSetupResponse(register);

        UserRegisterOutput userRegisterOutputCommand = userService.register(userRegisterInputCommand);

        //TODO: Make TokenGenerator so a token to the useer can be generated. Remove encoders and decoders from the controller!
        AuthenticationMetadata metadata = AuthenticationMetadata.builder()
                .setUserId(UUID.randomUUID().toString())
                .setEmail(register.email())
                .setUsername(register.username())
                .setUserRole(userRegisterOutputCommand.getRole())
                .setAuthorities(userRegisterOutputCommand.getAuthorities())
                .build();

        String token = encoder.encode(metadata);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserAuthenticatedResponse
                        .builder()
                        .email(register.email())
                        .data(userRegisterOutputCommand)
                        .token(token)
                        .build());
    }
}

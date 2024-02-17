package orchestrator.api.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.api.dto.user.UserLogin;
import orchestrator.api.dto.user.UserRegister;
import orchestrator.security.dto.UserAuthenticationDetails;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;

@UtilityClass
public final class DtoMapper {

    public static UserRegisterInput mapToRegisterInput(UserRegister dto) {

        return UserRegisterInput.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(dto.username())
                .email(dto.email())
                .password(dto.password())
                .build();
    }
    public static UserLoginInput mapToLoginInput(UserLogin dto) {

        return UserLoginInput.builder()
                .usernameOrEmail(dto.usernameOrEmail())
                .password(dto.password())
                .build();
    }

    public static UserAuthenticationDetails mapToUserAuthenticationDetails(UserProfileOutput command) {

        return UserAuthenticationDetails.builder()
                .userId(command.getId())
                .email(command.getEmail())
                .username(command.getUsername())
                .isActive(command.isActive())
                .role(command.getRole())
                .authorities(command.getAuthorities().stream().toList())
                .build();
    }
}

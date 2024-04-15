package orchestrator.api.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.api.dto.user.UserLogin;
import orchestrator.api.dto.user.UserRegister;
import orchestrator.api.dto.worker.PackageResult;
import orchestrator.api.dto.worker.IncomingPackage;
import orchestrator.security.dto.UserAuthenticationDetails;
import orchestrator.user.command.input.UserLoginInput;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
import orchestrator.worker.command.input.PackageInput;
import orchestrator.worker.command.output.PackageOutput;

import java.util.UUID;

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

    public static PackageInput mapToWorkerPackageInputCommand(IncomingPackage dtoPackage,
                                                              UUID userId,
                                                              UUID workerId,
                                                              UUID idempotencyKey) {

        return PackageInput.builder()
                .idempotencyKey(idempotencyKey)
                .userId(userId)
                .workerId(workerId)
                .discordServerId(dtoPackage.serverId())
                .action(dtoPackage.action())
                .content(dtoPackage.content())
                .build();
    }

    public static PackageResult mapToPackageResult(PackageOutput outputCommand) {

        return PackageResult.builder()
                .idempotencyKey(outputCommand.getIdempotencyKey())
                .status(outputCommand.getStatus())
                .build();
    }
}

package orchestrator.user.model.mapper;

import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import orchestrator.user.command.UserRegisterInput;
import orchestrator.user.command.UserRegisterOutput;
import orchestrator.user.model.User;

@UtilityClass
public class EntityMapper {

    public User mapToUser(UserRegisterInput registerCommand) {

        return User.builder()
                .firstName(registerCommand.getFirstName())
                .lastName(registerCommand.getLastName())
                .username(registerCommand.getUsername())
                .email(registerCommand.getEmail())
                .password(registerCommand.getPassword())
                .build();
    }

    public static UserRegisterOutput mapToRegisterOutput(User user) {

        return UserRegisterOutput.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .role(user.getRole().toString())
                .authorities(user.getAuthorities().stream().map(String::valueOf).collect(Collectors.toSet()))
                .createOn(user.getCreateOn())
                .updatedOn(user.getUpdatedOn())
                .build();
    }
}

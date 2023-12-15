package orchestrator.user.model.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.user.command.input.UserRegisterInput;
import orchestrator.user.command.output.UserProfileOutput;
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

    public static UserProfileOutput mapToProfileOutput(User user) {

        return UserProfileOutput.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .role(user.getRole())
                .authorities(user.getAuthorities())
                .createOn(user.getCreateOn())
                .updatedOn(user.getUpdatedOn())
                .build();
    }
}

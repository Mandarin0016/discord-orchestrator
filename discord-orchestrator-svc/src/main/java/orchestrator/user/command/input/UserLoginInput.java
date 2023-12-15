package orchestrator.user.command.input;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginInput {

    private final String usernameOrEmail;
    private final String password;

    @Builder
    public UserLoginInput(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}

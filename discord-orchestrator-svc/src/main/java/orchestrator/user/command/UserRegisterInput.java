package orchestrator.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRegisterInput {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;

    @Builder
    public UserRegisterInput(String firstName, String lastName, String username, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

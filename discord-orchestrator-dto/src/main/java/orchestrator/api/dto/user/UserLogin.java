package orchestrator.api.dto.user;

import jakarta.validation.constraints.Size;

public record  UserLogin(
        @Size(min = 5)
        String usernameOrEmail,
        @Size(min = 8)
        String password
) {

}

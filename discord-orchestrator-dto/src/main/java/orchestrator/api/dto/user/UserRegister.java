package orchestrator.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserRegister(
        @Size(min = 2)
        String firstName,
        @Size(min = 2)
        String lastName,
        @Size(min = 5)
        String username,
        @Email
        String email,
        @Size(min = 8)
        //TODO: validate password fancy way
        String password
) {

}

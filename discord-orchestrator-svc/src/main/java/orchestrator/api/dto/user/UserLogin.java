package orchestrator.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record  UserLogin(
        @Size(min = 5)
        @NotNull
        @NotBlank
        String usernameOrEmail,
        @Size(min = 8)
        @NotNull
        @NotBlank
        String password
) {

}

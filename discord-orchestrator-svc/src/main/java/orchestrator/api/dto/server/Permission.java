package orchestrator.api.dto.server;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record Permission(
        @NotBlank(message = "role cannot be blank")
        String role,
        @Valid
        List<PermissionAction> permissionActions
) {
}
package orchestrator.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record AccessRestriction(
        @NotBlank(message = "category cannot be blank")
        String category,
        @Valid
        List<String> channels,
        @Valid
        List<String> allowedRoles
) {
}

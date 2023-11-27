package orchestrator.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record Role(
        @NotBlank(message = "name cannot be blank")
        String name,
        String color,
        boolean hoist,
        boolean mentionable
) {
}
package orchestrator.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import orchestrator.api.validation.ValidHexColor;

@Builder
public record Role(
        @NotBlank(message = "name cannot be blank")
        @Size(max = 50)
        String name,
        @ValidHexColor
        String color,
        boolean hoist,
        boolean mentionable
) {
}
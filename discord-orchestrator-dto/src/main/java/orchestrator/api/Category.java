package orchestrator.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record Category(
        @NotBlank(message = "name cannot be blank")
        String name,
        String description,
        @Valid
        List<Channel> channels
) {
}

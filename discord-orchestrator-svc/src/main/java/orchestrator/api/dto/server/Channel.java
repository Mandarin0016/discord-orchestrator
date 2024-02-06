package orchestrator.api.dto.server;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record Channel(
        @NotBlank(message = "name cannot be blank")
        String name,
        @NotNull
        ChannelType type,
        String topic,
        @Valid
        List<Permission> permissions
) {
}

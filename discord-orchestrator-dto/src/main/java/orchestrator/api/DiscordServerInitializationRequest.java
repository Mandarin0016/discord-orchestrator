package orchestrator.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DiscordServerInitializationRequest(

        @NotNull(message = "discordServerId cannot be null")
        UUID discordServerId,
        @NotNull(message = "workerId cannot be null")
        UUID workerId,
        @Valid
        SetupDetails setupDetails
) {
}

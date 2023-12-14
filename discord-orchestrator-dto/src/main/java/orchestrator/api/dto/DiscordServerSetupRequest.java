package orchestrator.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DiscordServerSetupRequest(
        @NotNull(message = "idempotencyKey cannot be null")
        UUID idempotencyKey,
        @NotNull(message = "discordClientId cannot be null")
        UUID discordClientId,
        @NotNull(message = "workerId cannot be null")
        UUID workerId,
        @Valid
        SetupDetails setupDetails
) {
}

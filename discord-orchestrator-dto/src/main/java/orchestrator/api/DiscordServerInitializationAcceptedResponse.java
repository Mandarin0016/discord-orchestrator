package orchestrator.api;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record DiscordServerInitializationAcceptedResponse(
        @NotNull
        UUID trackingId,
        @NotNull
        UUID workerId,
        @NotNull
        OffsetDateTime createdOn
) {
}

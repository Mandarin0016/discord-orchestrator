package orchestrator.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

//TODO: finish response
@Builder
public record DiscordServerSetupResponse(
        @NotNull
        UUID trackingId,
        @NotNull
        UUID workerId,
        @NotNull
        OffsetDateTime createdOn
) {
}

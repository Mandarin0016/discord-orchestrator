package orchestrator.api.dto.server;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record DiscordServerSetupResponse(
        UUID id,
        UUID idempotencyKey,
        UUID trackingNumber,
        String discordServerId,
        UUID workerId,
        String status,
        OffsetDateTime createdOn,
        OffsetDateTime updatedOn,
        String createdBy,
        String updatedBy,
        String rejectedReason
) {

}

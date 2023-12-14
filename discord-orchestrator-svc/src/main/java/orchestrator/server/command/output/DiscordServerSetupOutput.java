package orchestrator.server.command.output;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiscordServerSetupOutput {

    private final UUID id;
    private final UUID idempotencyKey;
    private final UUID trackingNumber;
    private final String discordServerId;
    private final UUID workerId;
    private final SetupStatusOutput status;
    private final OffsetDateTime createdOn;
    private final OffsetDateTime updatedOn;
    private final String createdBy;
    private final String updatedBy;
    private String rejectedReason;

    @Builder
    public DiscordServerSetupOutput(UUID id, UUID idempotencyKey, UUID trackingNumber, String discordServerId,
            UUID workerId, SetupStatusOutput status, OffsetDateTime createdOn, OffsetDateTime updatedOn,
            String createdBy, String updatedBy) {

        this.id = id;
        this.idempotencyKey = idempotencyKey;
        this.trackingNumber = trackingNumber;
        this.discordServerId = discordServerId;
        this.workerId = workerId;
        this.status = status;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    @Builder
    public DiscordServerSetupOutput(UUID id, UUID idempotencyKey, UUID trackingNumber, String discordServerId,
            UUID workerId, SetupStatusOutput status, OffsetDateTime createdOn, OffsetDateTime updatedOn,
            String createdBy, String updatedBy, String rejectedReason) {

        this.id = id;
        this.idempotencyKey = idempotencyKey;
        this.trackingNumber = trackingNumber;
        this.discordServerId = discordServerId;
        this.workerId = workerId;
        this.status = status;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.rejectedReason = rejectedReason;
    }
}

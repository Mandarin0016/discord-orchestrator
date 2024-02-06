package orchestrator.worker.command.output;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class WorkerCreateOutput {

    private UUID id;
    private long clientId;
    private String name;
    private boolean isPurchasable;
    private boolean isReadyToUse;
    private OffsetDateTime createOn;
    private OffsetDateTime updatedOn;

    @Builder
    private WorkerCreateOutput(UUID id, long clientId, String name, boolean isPurchasable, boolean isReadyToUse, OffsetDateTime createOn, OffsetDateTime updatedOn) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.isPurchasable = isPurchasable;
        this.isReadyToUse = isReadyToUse;
        this.createOn = createOn;
        this.updatedOn = updatedOn;
    }
}

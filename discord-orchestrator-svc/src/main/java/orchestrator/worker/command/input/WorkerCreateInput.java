package orchestrator.worker.command.input;

import lombok.Builder;
import lombok.Data;

@Data
public class WorkerCreateInput {

    private long clientId;
    private String name;
    private boolean isPurchasable;
    private boolean isReadyToUse;

    @Builder
    private WorkerCreateInput(long clientId, String name, boolean isPurchasable, boolean isReadyToUse) {
        this.clientId = clientId;
        this.name = name;
        this.isPurchasable = isPurchasable;
        this.isReadyToUse = isReadyToUse;
    }
}

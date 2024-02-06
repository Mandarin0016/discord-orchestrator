package orchestrator.worker.command.input;

import lombok.Builder;
import lombok.Data;

@Data
public class WorkerBuyInput {

    private long clientId;
    private long teamId;
    private long serverId;

    @Builder
    private WorkerBuyInput(long clientId, long teamId, long serverId) {
        this.clientId = clientId;
        this.teamId = teamId;
        this.serverId = serverId;
    }
}

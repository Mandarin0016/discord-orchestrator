package orchestrator.worker.client;

import orchestrator.worker.client.WorkerClient;

import java.util.Map;
import java.util.UUID;

public class WorkerFactory {

    private final Map<UUID, WorkerClient> workerClients;

    public WorkerFactory(Map<UUID, WorkerClient> workerClients) {
        this.workerClients = workerClients;
    }

    public WorkerClient getWorkerClient(UUID workerId) {
        return workerClients.get(workerId);
    }
}

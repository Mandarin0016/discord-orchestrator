package orchestrator.worker.runner;

import orchestrator.worker.model.Worker;
import orchestrator.worker.property.WorkerProperties;
import orchestrator.worker.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean({WorkerProperties.class})
public class WorkerResolver implements CommandLineRunner {

    private final WorkerProperties workerProperties;
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerResolver(WorkerProperties workerProperties, WorkerRepository workerRepository) {
        this.workerProperties = workerProperties;
        this.workerRepository = workerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        workerProperties.getWorkers().forEach(this::buildAndSave);
    }

    private void buildAndSave(WorkerProperties.WorkerInfo workerInfo) {

        Worker worker = Worker.builder()
                .id(workerInfo.getId())
                .clientId(workerInfo.getClientId())
                .name(workerInfo.getName())
                .isPurchasable(workerInfo.getIsPurchasable())
                .isEnabled(workerInfo.getIsEnabled())
                .build();

        workerRepository.save(worker);
    }
}

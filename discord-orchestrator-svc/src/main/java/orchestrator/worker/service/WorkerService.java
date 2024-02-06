package orchestrator.worker.service;

import lombok.extern.slf4j.Slf4j;
import orchestrator.worker.command.input.WorkerCreateInput;
import orchestrator.worker.command.output.WorkerCreateOutput;
import orchestrator.worker.repository.WorkerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public WorkerCreateOutput create(WorkerCreateInput createInput) {


        return null;
    }

}

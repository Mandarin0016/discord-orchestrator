package orchestrator.worker.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import orchestrator.worker.command.input.PackageInput;
import orchestrator.worker.command.output.PackageOutput;
import orchestrator.worker.exception.WorkerDomainException;
import orchestrator.worker.model.Package;
import orchestrator.worker.repository.PackageRepository;
import orchestrator.worker.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static orchestrator.worker.model.mapper.EntityMapper.mapToPackageOutput;

@Service
@Slf4j
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final PackageRepository packageRepository;

    public WorkerService(WorkerRepository workerRepository,
                         PackageRepository packageRepository) {

        this.workerRepository = workerRepository;
        this.packageRepository = packageRepository;
    }

    @Transactional
    public PackageOutput savePackage(PackageInput packageInputCommand) {

        //TODO: Finish it
        UUID workerId = packageInputCommand.getWorkerId();
        if (!workerRepository.existsByIdAndIsEnabledIsTrue(workerId)) {
            throw new WorkerDomainException("Request attempt to inactive/not existing worker with id=[%s].".formatted(workerId));
        }

        Optional<Package> existingPackage = packageRepository.findByIdempotencyKey(packageInputCommand.getIdempotencyKey());
        if (existingPackage.isPresent()){
            return mapToPackageOutput(existingPackage.get());
        }

        return null;
    }
}

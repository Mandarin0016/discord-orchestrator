package orchestrator.worker.repository;

import orchestrator.worker.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, UUID> {

    boolean existsByIdAndEnabledIsTrue(UUID id);
}

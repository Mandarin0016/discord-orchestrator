package orchestrator.worker.repository;

import orchestrator.worker.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PackageRepository extends JpaRepository<Package, UUID> {

    Optional<Package> findByIdempotencyKey(UUID idempotencyKey);
}

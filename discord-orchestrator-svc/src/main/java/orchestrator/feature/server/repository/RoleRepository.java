package orchestrator.feature.server.repository;

import orchestrator.feature.server.model.ServerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<ServerRole, UUID> {
}

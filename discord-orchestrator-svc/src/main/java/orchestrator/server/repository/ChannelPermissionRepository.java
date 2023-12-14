package orchestrator.server.repository;

import orchestrator.server.model.ChannelPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelPermissionRepository extends JpaRepository<ChannelPermission, UUID> {
}

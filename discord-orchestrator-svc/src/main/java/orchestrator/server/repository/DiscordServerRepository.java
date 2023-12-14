package orchestrator.server.repository;

import java.util.UUID;
import orchestrator.server.model.DiscordServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordServerRepository extends JpaRepository<DiscordServer, UUID> {
}

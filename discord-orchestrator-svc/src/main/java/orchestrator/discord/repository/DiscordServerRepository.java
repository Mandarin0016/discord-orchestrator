package orchestrator.discord.repository;

import java.util.UUID;
import orchestrator.discord.model.DiscordServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordServerRepository extends JpaRepository<DiscordServer, UUID> {

}

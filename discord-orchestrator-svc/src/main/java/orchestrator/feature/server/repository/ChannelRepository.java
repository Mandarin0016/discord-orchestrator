package orchestrator.feature.server.repository;

import orchestrator.feature.server.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, UUID> {
}

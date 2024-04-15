package orchestrator.team.repository;

import orchestrator.team.model.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRoleRepository extends JpaRepository<TeamRole, UUID> {

    List<TeamRole> findByTeamId(UUID teamId);
}

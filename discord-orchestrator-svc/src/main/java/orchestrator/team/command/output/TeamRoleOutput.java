package orchestrator.team.command.output;

import lombok.Builder;
import orchestrator.team.model.TeamRoleAbility;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record TeamRoleOutput(
        UUID roleId,
        String name,
        String color,
        Set<TeamRoleAbility> roleAbilities,
        TeamMemberOutput createdBy,
        OffsetDateTime createdOn,
        TeamMemberOutput updatedBy,
        OffsetDateTime updatedOn
) {
}

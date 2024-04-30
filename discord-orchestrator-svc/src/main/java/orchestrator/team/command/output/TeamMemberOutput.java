package orchestrator.team.command.output;

import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record TeamMemberOutput(
        UUID memberId,
        String firstName,
        String lastName,
        String username,
        Set<UUID> roleIds,
        boolean isPrimaryOwner
) {
}

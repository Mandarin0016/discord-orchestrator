package orchestrator.team.command.input;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TeamCreateInput(
        String name,
        UUID ownerId
) {
}

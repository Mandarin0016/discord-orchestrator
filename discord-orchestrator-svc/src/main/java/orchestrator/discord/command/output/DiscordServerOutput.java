package orchestrator.discord.command.output;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record DiscordServerOutput(
        UUID id,
        UUID teamId,
        String name,
        String description,
        OffsetDateTime createdOn
) {
}

package orchestrator.team.command.output;

import lombok.Builder;
import orchestrator.discord.command.output.DiscordServerOutput;

import java.util.List;
import java.util.UUID;

@Builder
public record TeamOutput(

        UUID id,
        String name,
        TeamMemberOutput owner,
        List<TeamMemberOutput> members,
        List<UUID> workers,
        List<DiscordServerOutput> discordServers
) {

}

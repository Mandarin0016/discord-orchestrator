package orchestrator.discord.model.mapper;

import lombok.experimental.UtilityClass;
import orchestrator.discord.command.output.DiscordServerOutput;
import orchestrator.discord.model.DiscordServer;

@UtilityClass
public class DiscordServerEntityMapper {

    public static DiscordServerOutput toDiscordServerOutput(DiscordServer entity) {

        return DiscordServerOutput.builder()
                .id(entity.getId())
                .name(entity.getName())
                .teamId(entity.getTeam().getId())
                .description(entity.getDescription())
                .createdOn(entity.getCreatedOn())
                .build();
    }
}

package orchestrator.server.command.input;

import lombok.Builder;
import lombok.Getter;

import orchestrator.common.model.server.ChannelType;

import java.util.List;

@Getter
public class DiscordChannelInput {

    private final String name;
    private final ChannelType type;
    private final String topic;
    private final List<PermissionInput> permissions;

    @Builder
    private DiscordChannelInput(String name, ChannelType type, String topic, List<PermissionInput> permissions) {
        this.name = name;
        this.type = type;
        this.topic = topic;
        this.permissions = permissions;
    }
}

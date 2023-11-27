package orchestrator.api.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DiscordCategoryInput {

    private final String name;
    private final String description;
    private final List<DiscordChannelInput> channels;

    @Builder
    private DiscordCategoryInput(String name, String description, List<DiscordChannelInput> channels) {
        this.name = name;
        this.description = description;
        this.channels = channels;
    }
}

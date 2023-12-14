package orchestrator.server.command.input;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleInput {

    private final String name;
    private final String color;
    private final boolean hoist;
    private final boolean mentionable;

    @Builder
    private RoleInput(String name, String color, boolean hoist, boolean mentionable) {
        this.name = name;
        this.color = color;
        this.hoist = hoist;
        this.mentionable = mentionable;
    }
}

package orchestrator.command;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class DiscordServerSetupInput {

    private final UUID idempotencyKey;
    private final UUID discordClientId;
    private final String serverName;
    private final String serverDescription;
    private final String iconUrl;
    private final String bannerUrl;
    private final List<DiscordCategoryInput> categories;
    private final List<RoleInput> roles;

    @Builder
    private DiscordServerSetupInput(UUID idempotencyKey,
                                    UUID discordClientId,
                                    String serverName,
                                    String serverDescription,
                                    String iconUrl,
                                    String bannerUrl,
                                    List<DiscordCategoryInput> categories,
                                    List<RoleInput> roles) {

        this.idempotencyKey = idempotencyKey;
        this.discordClientId = discordClientId;
        this.serverName = serverName;
        this.serverDescription = serverDescription;
        this.iconUrl = iconUrl;
        this.bannerUrl = bannerUrl;
        this.categories = categories;
        this.roles = roles;
    }
}

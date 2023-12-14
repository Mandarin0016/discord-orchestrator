package orchestrator.api.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "discord.server.setup")
public class DiscordSetupProperties {

    @NotNull
    private Category category;
    @NotNull
    private Role role;

    @Data
    @Validated
    public static class Category {

        @Min(0)
        private int minCount;
        @Min(0)
        private int maxCount;
    }

    @Data
    @Validated
    public static class Role {

        @Min(0)
        private int minCount;
        @Min(0)
        private int maxCount;
    }
}

package orchestrator.worker.property;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Data
@Validated
@Configuration
@ConditionalOnProperty(value = "discord.worker.general.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "discord")
@PropertySource("/worker/worker-${spring.profiles.active}.properties")
public class WorkerProperties {

    @Valid
    @NotNull
    private final List<WorkerInfo> workers;

    @Data
    @Validated
    public static class WorkerInfo {
        @NotNull
        private UUID id;
        @NotNull
        private Long clientId;
        @NotBlank
        private String url;
        @NotBlank
        private String name;
        @NotNull
        private Boolean isEnabled;
        @NotNull
        private Boolean isPurchasable;
    }
}

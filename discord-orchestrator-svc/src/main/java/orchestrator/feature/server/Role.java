package orchestrator.feature.server;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = DiscordServer.class)
    private UUID discordServerId;
    @Size(min = 2)
    private String name;
    private String color;
    private boolean hoist;
    private boolean mentionable;
    @NotNull
    private OffsetDateTime createdOn;
    @NotNull
    private OffsetDateTime updatedOn;
    @NotNull
    private String updatedBy;
    @NotNull
    private String createdBy;


}

package orchestrator.feature.server;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = DiscordServer.class)
    private UUID discordServerId;
    @Size(min = 2)
    String name;
    @Size(min = 2)
    private String description;
    @OneToMany
    private List<Channel> channels;
    @NotNull
    private OffsetDateTime createdOn;
    @NotNull
    private OffsetDateTime updatedOn;
    @NotNull
    private String updatedBy;
    @NotNull
    private String createdBy;

}

package orchestrator.feature.server;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orchestrator.common.model.ChannelType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Category.class)
    private UUID categoryId;
    @Size(min = 2)
    private String name;
    @Enumerated(EnumType.STRING)
    private ChannelType channelType;
    @Size(min = 2)
    private String topic;
    @OneToMany
    private List<ChannelPermissions> channelPermissions;
    @NotNull
    private OffsetDateTime createdOn;
    @NotNull
    private OffsetDateTime updatedOn;
    @NotNull
    private String updatedBy;
    @NotNull
    private String createdBy;

}

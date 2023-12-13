package orchestrator.feature.server.model;
import jakarta.persistence.*;
import lombok.*;
import orchestrator.feature.server.Action;

import java.util.List;
import java.util.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChannelPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Channel.class)
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    private UUID channelId;
    @ManyToOne(targetEntity = ServerRole.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UUID roleId;
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private List<Action> actions;

}

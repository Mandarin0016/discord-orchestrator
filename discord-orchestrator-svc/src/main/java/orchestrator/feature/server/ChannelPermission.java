package orchestrator.feature.server;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Entity
@Table
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
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UUID roleId;
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private List<Action> actions;

}

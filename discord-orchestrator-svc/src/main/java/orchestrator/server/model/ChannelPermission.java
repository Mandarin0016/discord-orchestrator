package orchestrator.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Channel.class)
    @JoinColumn(name = "channel_id", referencedColumnName = "id", nullable = false)
    private UUID channelId;
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private UUID roleId;
    @ElementCollection(targetClass = Action.class, fetch = FetchType.EAGER)
    private List<Action> actions;
}

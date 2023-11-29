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
public class ChannelPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Channel.class)
    private UUID channelId;
    @ManyToOne(targetEntity = Role.class)
    private UUID roleId;
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private List<Action> actions;

}

package orchestrator.worker.model;

import com.mandarin.dto.Action;
import com.mandarin.dto.Status;
import jakarta.persistence.*;
import lombok.*;
import orchestrator.discord.model.DiscordServer;
import orchestrator.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private UUID idempotencyKey;
    @ManyToOne(targetEntity = DiscordServer.class, optional = false)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "server_id")
    private UUID serverId;
    @ManyToOne(targetEntity = Worker.class, optional = false)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "worker_id")
    private UUID workerId;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "user_id")
    private UUID userId;
    @Column(nullable = false)
    private Action action;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private String content;
    private String workerResponse;
    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createOn;
    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedOn;
}

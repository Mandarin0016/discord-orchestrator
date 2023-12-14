package orchestrator.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = DiscordServer.class)
    @JoinColumn(name = "discord_server_id", referencedColumnName = "id")
    private UUID discordServerId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String color;
    private boolean hoist;
    private boolean mentionable;
    @Column(nullable = false)
    @CreatedDate
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @CreatedBy
    private String createdBy;
    @Column(nullable = false)
    @LastModifiedDate
    private OffsetDateTime updatedOn;
    @Column(nullable = false)
    @LastModifiedBy
    private String updatedBy;
}

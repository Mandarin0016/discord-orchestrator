package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orchestrator.discord.model.DiscordServer;
import orchestrator.user.model.User;
import orchestrator.worker.model.Worker;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "owner_id")
    private UUID ownerId;
    @ManyToMany
    @JoinTable(
            name = "team_members",
            joinColumns = { @JoinColumn(name = "team_id") },
            inverseJoinColumns = { @JoinColumn(name = "member_id") }
    )
    private List<User> members;
    @ManyToMany
    @JoinTable(
            name = "team_workers",
            joinColumns = { @JoinColumn(name = "team_id") },
            inverseJoinColumns = { @JoinColumn(name = "worker_id") }
    )
    private List<Worker> workers;
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<DiscordServer> servers;
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<TeamRole> teamRoles;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
}

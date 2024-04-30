package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.*;
import orchestrator.discord.model.DiscordServer;
import orchestrator.user.model.User;
import orchestrator.worker.model.Worker;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
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
    private User owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "team_workers",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "worker_id")}
    )
    private List<Worker> workers = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<DiscordServer> servers = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<TeamRole> teamRoles = new ArrayList<>();
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
    private boolean isActive;
}

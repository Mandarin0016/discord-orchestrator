package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.*;
import orchestrator.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Team.class, optional = false)
    @JoinColumn(nullable = false, name = "team_id", referencedColumnName = "id")
    private Team team;
    @Column(nullable = false)
    private String name;
    private String color;
    @ElementCollection(targetClass = TeamRoleAbility.class, fetch = FetchType.EAGER)
    @Column(name = "ability", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Set<TeamRoleAbility> abilities;
    @Enumerated(value = EnumType.STRING)
    private TeamRoleRank roleRank;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(nullable = false, name = "created_by", referencedColumnName = "id")
    private User createdBy;
    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(nullable = false, name = "updated_by", referencedColumnName = "id")
    private User updatedBy;
}

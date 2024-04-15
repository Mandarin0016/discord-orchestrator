package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Team.class, optional = false)
    @JoinColumn(nullable = false, name = "team_id", referencedColumnName = "id")
    private UUID teamId;
    @Column(nullable = false)
    private String name;
    private String color;
    @ElementCollection(targetClass = TeamRoleAbility.class, fetch = FetchType.EAGER)
    @Column(name = "ability", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Set<TeamRoleAbility> abilities;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
}

package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.*;
import orchestrator.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "member_id")
    private User member;
    @ManyToMany
    @JoinTable(
            name = "team_member_roles",
            joinColumns = {@JoinColumn(name = "membership_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<TeamRole> memberRoles = new HashSet<>();
    private boolean isPrimaryOwner;
    private TeamMembershipStatus membershipStatus;
    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdOn;
    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedOn;
}

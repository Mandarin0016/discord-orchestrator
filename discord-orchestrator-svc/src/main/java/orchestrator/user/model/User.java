package orchestrator.user.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import orchestrator.team.model.TeamRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String discordId;
    @Column(nullable = false)
    private boolean isDiscordAuthorized;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @ElementCollection(targetClass = UserAuthority.class, fetch = FetchType.EAGER)
    @Column(name = "authority", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Set<UserAuthority> authorities;
    @ManyToMany(targetEntity = TeamRole.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "team_member_roles",
            joinColumns = { @JoinColumn(name = "member_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_role_id") }
    )
    private Set<TeamRole> teamRoles;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
}

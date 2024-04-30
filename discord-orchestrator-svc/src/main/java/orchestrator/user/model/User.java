package orchestrator.user.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import orchestrator.team.model.Team;
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
    @ElementCollection(targetClass = SystemAuthority.class, fetch = FetchType.EAGER)
    @Column(name = "authority", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Set<SystemAuthority> authorities;
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

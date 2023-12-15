package orchestrator.user.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;
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

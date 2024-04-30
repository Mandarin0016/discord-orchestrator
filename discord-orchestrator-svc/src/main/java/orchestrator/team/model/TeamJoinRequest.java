package orchestrator.team.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orchestrator.user.model.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "user_id")
    private User requester;
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "team_id")
    private Team team;
    @Column(nullable = false)
    private TeamJoinRequestStatus status;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
}
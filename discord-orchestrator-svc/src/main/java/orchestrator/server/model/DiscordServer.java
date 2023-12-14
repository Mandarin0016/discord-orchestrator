package orchestrator.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List;
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
public class DiscordServer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String clientId;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany
    private List<Category> categories;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
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

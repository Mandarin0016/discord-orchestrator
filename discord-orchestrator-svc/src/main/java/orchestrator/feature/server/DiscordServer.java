package orchestrator.feature.server;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DiscordServer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @UniqueElements
    private String clientId;
    private String name;
    private String description;
    @OneToMany
    private List<Category> categories;
    @OneToMany
    private List<Role> roles;
    @NotNull
    private OffsetDateTime createdOn;
    @NotNull
    private OffsetDateTime updatedOn;
    @NotNull
    private String updatedBy;
    @NotNull
    private String createdBy;


}

package orchestrator.worker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    @Id
    private UUID id;
    @Column(nullable = false)
    private long clientId;
    @Column(nullable = false)
    private String name;
    private boolean isPurchasable;
    private boolean isEnabled;
    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createOn;
    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedOn;
}

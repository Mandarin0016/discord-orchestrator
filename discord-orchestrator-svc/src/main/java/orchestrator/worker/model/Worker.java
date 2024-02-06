package orchestrator.worker.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private long clientId;
    @Column(nullable = false)
    private String name;
    private boolean isPurchasable;
    private boolean isReadyToUse;
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createOn;
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedOn;
}

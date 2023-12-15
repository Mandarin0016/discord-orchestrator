package orchestrator.server.model;

import jakarta.persistence.*;
import lombok.*;
import orchestrator.common.model.server.ChannelType;

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
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private UUID categoryId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelType channelType;
    @Column(nullable = false)
    private String topic;
    @OneToMany
    private List<ChannelPermission> channelPermissions;
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

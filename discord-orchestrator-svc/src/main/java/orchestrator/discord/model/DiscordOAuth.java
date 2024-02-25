package orchestrator.discord.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discord_oauth")
public class DiscordOAuth {

    public DiscordOAuth(UUID userId) {
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private UUID userId;
    @Column(nullable = false)
    private String discordUserId;
    @Column(nullable = false)
    private String tokenType;
    @Column(nullable = false)
    private String accessToken;
    @Column(nullable = false)
    private String refreshToken;
    @Column(nullable = false)
    private OffsetDateTime expireAt;
    @Column(nullable = false)
    private String scopes;
}

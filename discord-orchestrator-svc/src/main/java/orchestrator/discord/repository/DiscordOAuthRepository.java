package orchestrator.discord.repository;

import orchestrator.discord.model.DiscordOAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DiscordOAuthRepository extends JpaRepository<DiscordOAuth, UUID> {

    @Query("""
            SELECT doa FROM DiscordOAuth doa
            WHERE doa.userId = ?1
            """)
    Optional<DiscordOAuth> findByUserId(UUID userId);
}

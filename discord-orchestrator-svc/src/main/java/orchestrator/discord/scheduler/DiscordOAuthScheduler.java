package orchestrator.discord.scheduler;

import lombok.extern.slf4j.Slf4j;
import orchestrator.discord.model.DiscordOAuth;
import orchestrator.discord.repository.DiscordOAuthRepository;
import orchestrator.discord.service.DiscordOAuthService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class DiscordOAuthScheduler {

    private final DiscordOAuthRepository discordOAuthRepository;
    private final DiscordOAuthService discordOAuthService;

    public DiscordOAuthScheduler(DiscordOAuthRepository discordOAuthRepository, DiscordOAuthService discordOAuthService) {
        this.discordOAuthRepository = discordOAuthRepository;
        this.discordOAuthService = discordOAuthService;
    }

    @Scheduled(cron = "0 0 * ? * *")
    public void refreshDiscordAccessToken() {

        log.info("Fetching all pending authorizations for refresh.");
        List<DiscordOAuth> authorizations = discordOAuthRepository.findAllWhereExpireAtIsOneDayAhead();

        log.info("Pending authorizations for refresh: %d".formatted(authorizations.size()));
        for (DiscordOAuth authorization : authorizations) {
            UUID userId = authorization.getUserId();
            String refreshToken = authorization.getRefreshToken();
            discordOAuthService.authorizeUser(userId, null, refreshToken);
        }
    }
}

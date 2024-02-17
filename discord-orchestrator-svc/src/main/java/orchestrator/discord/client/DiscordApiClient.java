package orchestrator.discord.client;

import orchestrator.discord.client.dto.DiscordUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "discordApiClient", url = "https://discord.com/api/v10")
public interface DiscordApiClient {

    @GetMapping(path = "/users/@me")
    DiscordUser getUserDetails(@RequestHeader("Authorization") String authorizationHeader);
}

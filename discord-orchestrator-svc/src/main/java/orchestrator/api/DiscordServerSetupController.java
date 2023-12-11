package orchestrator.api;

import orchestrator.api.dto.DiscordServerSetupRequest;
import orchestrator.api.dto.DiscordServerSetupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import static orchestrator.api.Paths.BASE_PATH_V1;

@RestController
@RequestMapping(BASE_PATH_V1 + "/discord/servers")
public class DiscordServerSetupController {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DiscordServerSetupResponse createSetupRequest(@Validated @RequestBody DiscordServerSetupRequest request) {

        return new DiscordServerSetupResponse(UUID.randomUUID(), request.workerId(), OffsetDateTime.now());
    }

    @GetMapping("{trackingId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getInitializationRequestStatus(@PathVariable("trackingId") UUID trackingId) {

        return "In Progress...";
    }
}

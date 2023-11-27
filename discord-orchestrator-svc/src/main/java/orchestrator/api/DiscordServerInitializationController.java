package orchestrator.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import static orchestrator.api.Paths.BASE_PATH_V1;

@RestController
@RequestMapping(BASE_PATH_V1 + "/discord/servers")
public class DiscordServerInitializationController {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DiscordServerInitializationAcceptedResponse create(@RequestBody DiscordServerInitializationRequest request) {

        return new DiscordServerInitializationAcceptedResponse(UUID.randomUUID(), request.workerId(), OffsetDateTime.now());
    }

    @GetMapping("{trackingId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getInitializationRequestStatus(@PathVariable("trackingId") UUID trackingId) {

        return "In Progress...";
    }
}

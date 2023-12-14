package orchestrator.api;

import orchestrator.api.dto.DiscordServerSetupRequest;
import orchestrator.api.mapper.DtoMapper;
import orchestrator.server.command.input.DiscordServerSetupInput;
import orchestrator.server.command.output.DiscordServerSetupOutput;
import orchestrator.server.service.DiscordServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.web.util.UriComponentsBuilder;

import static orchestrator.api.MediaType.DISCORD_SETUP_REQUEST;
import static orchestrator.api.MediaType.DISCORD_SETUP_RESPONSE;
import static orchestrator.api.Paths.BASE_PATH_V1;

@RestController
@RequestMapping(BASE_PATH_V1 + "/discord/servers")
public class DiscordServerSetupController {

    private final DiscordServerService discordServerService;

    @Autowired
    public DiscordServerSetupController(DiscordServerService discordServerService) {

        this.discordServerService = discordServerService;
    }

    @PostMapping(consumes = DISCORD_SETUP_REQUEST, produces = DISCORD_SETUP_RESPONSE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<DiscordServerSetupOutput> createSetupRequest(@Validated @RequestBody DiscordServerSetupRequest request) {

        DiscordServerSetupInput discordServerSetupInputCommand = DtoMapper.mapToServerSetupInput(request);

        DiscordServerSetupOutput discordServerSetupOutput = discordServerService.create(discordServerSetupInputCommand);

        return ResponseEntity
                .created(UriComponentsBuilder.fromPath("?trackingNumber={trackingNumber}").buildAndExpand().toUri())
                .body(discordServerSetupOutput);
    }

    @GetMapping(produces = DISCORD_SETUP_RESPONSE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<DiscordServerSetupOutput> getTrackingInformation(@PathVariable UUID trackingNumber) {


        return null;
    }
}

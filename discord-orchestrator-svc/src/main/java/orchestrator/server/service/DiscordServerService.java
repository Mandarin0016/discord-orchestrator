package orchestrator.server.service;

import orchestrator.server.command.input.DiscordServerSetupInput;
import orchestrator.server.command.output.DiscordServerSetupOutput;
import orchestrator.server.model.DiscordServer;
import orchestrator.server.repository.DiscordServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DiscordServerService {

    private final DiscordServerRepository discordServerRepository;

    public DiscordServerService(DiscordServerRepository discordServerRepository) {
        this.discordServerRepository = discordServerRepository;
    }

    @Transactional
    public DiscordServerSetupOutput create(DiscordServerSetupInput inputCommand){

        return null;
    }

}

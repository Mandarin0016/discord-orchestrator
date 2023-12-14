package orchestrator.server.service;

import orchestrator.server.command.input.DiscordServerSetupInput;
import orchestrator.server.command.output.DiscordServerSetupOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiscordServerService {

    @Transactional
    public DiscordServerSetupOutput create(DiscordServerSetupInput inputCommand){

        return null;
    }

}

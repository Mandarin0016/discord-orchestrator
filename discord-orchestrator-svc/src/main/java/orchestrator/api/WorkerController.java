package orchestrator.api;

import orchestrator.worker.client.GaladrielClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final GaladrielClient galadrielClient;

    public WorkerController(GaladrielClient galadrielClient) {
        this.galadrielClient = galadrielClient;
    }

    @GetMapping
    public String getGaladrielName() {
        return galadrielClient.getInformationFromGaladriel("discord-orchestrator");
    }

}

package orchestrator.worker.client;


import com.mandarin.client.DiscordWorkerBaseController;
import com.mandarin.dto.Package;
import org.springframework.web.bind.annotation.PostMapping;

public interface WorkerClient extends DiscordWorkerBaseController {
    @Override
    @PostMapping("/packages")
    Package sendPackage(Package pack);
}

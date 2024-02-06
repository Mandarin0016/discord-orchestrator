package orchestrator.worker.client;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GaladrielClient {

    @GetMapping(value = "/demo")
    String getInformationFromGaladriel(@RequestParam("service") String service);
}

package orchestrator.worker.command.output;

import com.mandarin.dto.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class PackageOutput {

    private UUID idempotencyKey;
    private Status status;
    private String workerResponse;
}

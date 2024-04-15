package orchestrator.worker.command.input;

import com.mandarin.dto.Action;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class PackageInput {

    private UUID idempotencyKey;
    private UUID userId;
    private UUID workerId;
    private UUID discordServerId;
    private Action action;
    private String content;
}

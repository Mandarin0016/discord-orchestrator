package orchestrator.api.dto.worker;

import com.mandarin.dto.Action;
import jakarta.validation.constraints.NotNull;
import orchestrator.team.validation.TeamAuthorized;

import java.util.UUID;

@TeamAuthorized
public record IncomingPackage(
        @NotNull
        UUID serverId,
        @NotNull
        Action action,
        @NotNull
        String content
) {
}

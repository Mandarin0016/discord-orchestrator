package orchestrator.api;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ErrorResponse(
        UUID errorCode,
        OffsetDateTime time,
        String data
) {

}

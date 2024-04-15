package orchestrator.api.dto.worker;

import com.mandarin.dto.Status;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PackageResult(
        UUID idempotencyKey,
        Status status,
        String data
) {
}

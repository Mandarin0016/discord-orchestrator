package orchestrator.api;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ErrorResponse(
        UUID errorCode,
        @JsonProperty("timestamp")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        OffsetDateTime time,
        String data
) {

}

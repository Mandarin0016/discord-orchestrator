package orchestrator.security.dto;

import lombok.Builder;

@Builder
public record UserAuthenticatedResponse(
        String email,
        String token,
        Object data)
{

}

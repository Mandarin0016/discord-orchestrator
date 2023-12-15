package orchestrator.security.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;

@Builder
public record UserAuthenticationDetails(
        UUID userId,
        String email,
        String username,
        boolean isActive,
        UserRole role,
        List<UserAuthority> authorities)
{

}

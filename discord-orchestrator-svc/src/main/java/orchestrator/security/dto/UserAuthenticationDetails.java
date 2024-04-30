package orchestrator.security.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import orchestrator.user.model.SystemAuthority;
import orchestrator.user.model.UserRole;

@Builder
public record UserAuthenticationDetails(
        UUID userId,
        String email,
        String username,
        boolean isActive,
        UserRole role,
        List<SystemAuthority> authorities) {

}

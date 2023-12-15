package orchestrator.user.command.output;


import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import orchestrator.common.model.user.UserAuthority;
import orchestrator.common.model.user.UserRole;

@Builder
@Getter
public class UserProfileOutput {

    private UUID id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private boolean isActive;
    private UserRole role;
    private Set<UserAuthority> authorities;
    private OffsetDateTime createOn;
    private OffsetDateTime updatedOn;
}

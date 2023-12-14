package orchestrator.user.command;


import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRegisterOutput {

    private UUID id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private boolean isActive;
    private String role;
    private Set<String> authorities;
    private OffsetDateTime createOn;
    private OffsetDateTime updatedOn;
}

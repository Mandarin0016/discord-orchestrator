package orchestrator.user.property;

import java.util.Set;
import lombok.Data;
import orchestrator.user.model.SystemAuthority;
import orchestrator.user.model.UserRole;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "user")
public class UserProperties {

    private UserRole defaultRole;
    private Set<SystemAuthority> defaultAuthorities;
    private boolean defaultAccountState;

    public UserRole getDefaultRole() {
        return defaultRole;
    }

    public Set<SystemAuthority> getDefaultAuthorities() {
        return defaultAuthorities;
    }

    public boolean getDefaultAccountState() {
        return defaultAccountState;
    }
}

package orchestrator.security.jwt.auth;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private String role;

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}

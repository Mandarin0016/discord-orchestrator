package orchestrator.security.jwt.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    @JsonProperty("authority")
    private String role;

    public Authority(){

    }

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}

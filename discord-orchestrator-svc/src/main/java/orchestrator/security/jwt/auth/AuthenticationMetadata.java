package orchestrator.security.jwt.auth;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationMetadata implements UserDetails {

    private String username;
    private String email;
    private String userRole;
    private String userId;
    private boolean isActive;
    private Set<Authority> authorities = new HashSet<>();
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public void setActive(boolean active) {

        isActive = active;
    }

    private AuthenticationMetadata() {

    }

    private AuthenticationMetadata(
            String username,
            String email,
            String userRole,
            String userId,
            Set<Authority> authorities,
            boolean isActive) {

        this.username = username;
        this.email = email;
        this.userRole = userRole;
        this.userId = userId;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {

        return null;
    }

    @Override
    public String getUsername() {

        return username;
    }

    public String getEmail() {

        return email;
    }

    public String getUserRole() {

        return userRole;
    }

    public String getUserId() {

        return userId;
    }

    public boolean isActive() {

        return isActive;
    }

    @Override
    public boolean isAccountNonExpired() {

        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {

        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {

        return isActive;
    }

    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {

        private String username;
        private String email;
        private String userRole;
        private String userId;
        private boolean isActive;
        private Set<Authority> authorities;

        private Builder() {

        }

        public Builder setUsername(String username) {

            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {

            this.email = email;
            return this;
        }

        public Builder setUserRole(String userRole) {

            this.userRole = userRole;
            return this;
        }

        public Builder setActive(boolean isActive) {

            this.isActive = isActive;
            return this;
        }

        public Builder setUserId(String userId) {

            this.userId = userId;
            return this;
        }

        public Builder setAuthorities(Set<Authority> authorities) {

            this.authorities = authorities;
            return this;
        }

        public AuthenticationMetadata build() {

            return new AuthenticationMetadata(
                    this.username,
                    this.email,
                    this.userRole,
                    this.userId,
                    this.authorities,
                    this.isActive
            );
        }
    }
}

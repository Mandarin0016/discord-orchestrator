package orchestrator.security.jwt.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private boolean isAuthenticated;
    private final String jwt;
    private AuthenticationMetadata metadata;

    public JwtAuthentication(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public String getName() {
        return metadata.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return metadata.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return metadata;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    public void setMetadata(AuthenticationMetadata authenticationMetadata) {
        this.metadata = authenticationMetadata;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}

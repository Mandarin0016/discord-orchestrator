package orchestrator.security.jwt;

import java.util.stream.Collectors;
import orchestrator.security.AuthorizationGenerator;
import orchestrator.security.dto.UserAuthenticationDetails;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.security.jwt.auth.Authority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationGenerator implements AuthorizationGenerator {

    private final AuthenticationJwtEncoder encoder;

    public JwtAuthorizationGenerator(AuthenticationJwtEncoder encoder) {

        this.encoder = encoder;
    }

    @Override
    public String generateAuthorizationHeader(UserAuthenticationDetails details) {

        AuthenticationMetadata metadata = AuthenticationMetadata.builder()
                .setUserId(details.userId().toString())
                .setEmail(details.email())
                .setUsername(details.username())
                .setUserRole(details.role().name())
                .setAuthorities(details.authorities().stream().map(ua -> new Authority(ua.name())).collect(Collectors.toSet()))
                .setActive(details.isActive())
                .build();

        return encoder.encode(metadata);
    }
}

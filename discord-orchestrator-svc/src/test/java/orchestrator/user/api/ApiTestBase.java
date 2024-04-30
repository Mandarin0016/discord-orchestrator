package orchestrator.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import orchestrator.api.ExceptionAdvice;
import orchestrator.user.model.SystemAuthority;
import orchestrator.user.model.UserRole;
import orchestrator.config.JacksonConfiguration;
import orchestrator.config.security.SecurityConfiguration;
import orchestrator.security.dto.UserAuthenticationDetails;
import orchestrator.security.jwt.AuthenticationJwtDecoder;
import orchestrator.security.jwt.AuthenticationJwtEncoder;
import orchestrator.security.jwt.JwtAuthorizationGenerator;
import orchestrator.security.jwt.manager.AuthenticationJwtManager;
import orchestrator.security.jwt.properties.JwtSecurityProperties;
import orchestrator.security.jwt.provider.AuthenticationJwtProvider;
import orchestrator.security.jwt.verifier.AuthenticationJwtClaimsVerifier;
import orchestrator.security.jwt.verifier.AuthenticationJwtSignatureVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static orchestrator.user.model.SystemAuthority.DEFAULT_VIEW;

@Import({
        ExceptionAdvice.class,
        JacksonConfiguration.class,
        SecurityConfiguration.class,
        JwtAuthorizationGenerator.class,
        AuthenticationJwtEncoder.class,
        AuthenticationJwtDecoder.class,
        AuthenticationJwtManager.class,
        AuthenticationJwtProvider.class,
        AuthenticationJwtClaimsVerifier.class,
        AuthenticationJwtSignatureVerifier.class,
        JwtSecurityProperties.class
})
@ActiveProfiles("test")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ImportAutoConfiguration(PropertySourcesPlaceholderConfigurer.class)
public abstract class ApiTestBase {

    protected static final String HTTP_AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtAuthorizationGenerator jwtAuthorizationGenerator;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeEach
    protected void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    protected String createValidJwtToken() {

        return createValidJwtToken(UserRole.USER, Set.of(DEFAULT_VIEW));
    }

    protected String createValidJwtToken(UserRole role) {

        return createValidJwtToken(role, Set.of(DEFAULT_VIEW));
    }

    protected String createValidJwtToken(Set<SystemAuthority> authorities) {

        return createValidJwtToken(UserRole.USER, authorities);
    }

    protected String createValidJwtToken(UserRole role, Set<SystemAuthority> authorities) {

        UserAuthenticationDetails authenticationDetails = UserAuthenticationDetails.builder()
                .userId(UUID.randomUUID())
                .email("user@gmail.com")
                .username("user1234")
                .isActive(true)
                .role(role)
                .authorities(new ArrayList<>(authorities))
                .build();

        return jwtAuthorizationGenerator.generateAuthorizationHeader(authenticationDetails);
    }
}

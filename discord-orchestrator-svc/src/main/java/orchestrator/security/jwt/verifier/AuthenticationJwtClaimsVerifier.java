package orchestrator.security.jwt.verifier;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import net.jcip.annotations.ThreadSafe;
import orchestrator.security.jwt.AuthenticationJwtEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ThreadSafe
@Component
public final class AuthenticationJwtClaimsVerifier<C extends SecurityContext> extends DefaultJWTClaimsVerifier<C> {

    private static final JWTClaimsSet EXACT_MATCH_CLAIMS = (new JWTClaimsSet.Builder())
            .issuer("stride")
            .claim("author", AuthenticationJwtEncoder.AUTHOR_JWT_CLAIM)
            .claim("version", AuthenticationJwtEncoder.VERSION_JWT_CLAIM)
            .build();
    private static final Set<String> REQUIRED_CLAIMS = new HashSet(Arrays.asList("data"));

    public AuthenticationJwtClaimsVerifier() {
        super(EXACT_MATCH_CLAIMS, REQUIRED_CLAIMS);
    }

    @Override
    public void verify(JWTClaimsSet claimsSet, C context) throws BadJWTException {
        super.verify(claimsSet, context);
    }
}
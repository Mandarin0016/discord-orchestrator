package orchestrator.security.jwt.verifier;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import java.util.Map.Entry;
import net.jcip.annotations.ThreadSafe;
import orchestrator.security.jwt.properties.JwtSecurityProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@ThreadSafe
@Component
public final class AuthenticationJwtClaimsVerifier<C extends SecurityContext> extends DefaultJWTClaimsVerifier<C> {

    private static final Set<String> REQUIRED_CLAIMS = Set.of("data");
    private final JwtSecurityProperties jwtProperties;
    private final JWTClaimsSet exactMatchClaims;

    public AuthenticationJwtClaimsVerifier(JwtSecurityProperties jwtProperties) {
        super((new JWTClaimsSet.Builder()).build(), REQUIRED_CLAIMS);
        this.jwtProperties = jwtProperties;
        this.exactMatchClaims = retrieveExactMatchClaims();
    }

    @Override
    public void verify(JWTClaimsSet claimsSet, C context) throws BadJWTException {

        super.verify(claimsSet, context);

        for (String exactMatch: exactMatchClaims.getClaims().keySet()) {
            Object actualClaim = claimsSet.getClaim(exactMatch);
            Object expectedClaim = exactMatchClaims.getClaim(exactMatch);
            if (! actualClaim.equals(expectedClaim)) {
                throw new BadJWTException("JWT " + exactMatch + " claim has value " + actualClaim + ", must be " + expectedClaim);
            }
        }
    }

    private JWTClaimsSet retrieveExactMatchClaims(){

        Builder builder = new Builder();
        Set<Entry<String, String>> claims = jwtProperties.getClaims().entrySet();
        for (Entry<String, String> claimEntry : claims) {
            builder.claim(claimEntry.getKey(), claimEntry.getValue());
        }
        return builder.build();
    }
}
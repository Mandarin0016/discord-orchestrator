package orchestrator.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.SignedJWT;
import java.util.Map.Entry;
import orchestrator.security.Encoder;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.security.jwt.properties.JwtSecurityProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.nimbusds.jose.JWSAlgorithm.RS256;


@Component
public class AuthenticationJwtEncoder implements Encoder<AuthenticationMetadata> {

    public static final JWSAlgorithm EXPECTED_ALGORITHM = RS256;
    private final JwtSecurityProperties jwtProperties;
    private final JWSSigner signer;
    private final ObjectMapper objectMapper;

    public AuthenticationJwtEncoder(JwtSecurityProperties jwtProperties, @Qualifier("privateJWK") RSAKey rsaPrivateJWK, ObjectMapper objectMapper) throws JOSEException {

        this.jwtProperties = jwtProperties;
        this.signer = new RSASSASigner(rsaPrivateJWK);
        this.objectMapper = objectMapper;
    }

    @Override
    public String encode(AuthenticationMetadata data) {

        Builder claimBuilder = new Builder()
                .subject(data.getEmail())
                .issuer(jwtProperties.getIssuer())
                .expirationTime(new Date(new Date().getTime() + jwtProperties.getTokenExpirationInMinutes() * 60000L));

        for (Entry<String, String> claimEntry : jwtProperties.getClaims().entrySet()) {
            claimBuilder.claim(claimEntry.getKey(), claimEntry.getValue());
        }

        String dataAsJson;
        try {
            dataAsJson = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        claimBuilder.claim("data", dataAsJson);

        JWTClaimsSet claimsSet = claimBuilder.build();
        JWSHeader header = new JWSHeader.Builder(EXPECTED_ALGORITHM).type(JOSEObjectType.JWT).build();

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwt.serialize();
    }
}

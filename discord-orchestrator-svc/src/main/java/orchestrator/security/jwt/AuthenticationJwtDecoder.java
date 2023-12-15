package orchestrator.security.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.proc.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import orchestrator.security.jwt.properties.JwtSecurityProperties;
import orchestrator.security.jwt.verifier.AuthenticationJwtSignatureVerifier;
import orchestrator.security.jwt.verifier.AuthenticationJwtClaimsVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import orchestrator.security.Decoder;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.security.jwt.excpetion.InvalidBearerTokenException;

import java.io.IOException;
import java.text.ParseException;

@Service
public class AuthenticationJwtDecoder implements Decoder<AuthenticationMetadata> {

    private static final String BEARER_PREFIX = "Bearer ";
    private final ObjectMapper objectMapper;
    private final AuthenticationJwtClaimsVerifier<SecurityContext> claimsVerifier;
    private final AuthenticationJwtSignatureVerifier signatureVerifier;
    private final JwtSecurityProperties jwtProperties;

    @Autowired
    public AuthenticationJwtDecoder(AuthenticationJwtSignatureVerifier signatureVerifier,
            JwtSecurityProperties jwtProperties) {

        this.signatureVerifier = signatureVerifier;
        this.jwtProperties = jwtProperties;
        this.claimsVerifier = new AuthenticationJwtClaimsVerifier<>(this.jwtProperties);
        this.objectMapper = new ObjectMapper();
    }

    public AuthenticationMetadata decode(String token) throws InvalidBearerTokenException {

        try {
            if (!StringUtils.hasLength(token)) {
                throw new InvalidBearerTokenException("Missing token");
            } else if (!token.startsWith(BEARER_PREFIX)) {
                throw new InvalidBearerTokenException("Token not formatted correctly");
            } else {
                String jwtString = token.substring(BEARER_PREFIX.length());

                if (!StringUtils.hasLength(jwtString)) {
                    throw new InvalidBearerTokenException("Missing token");
                }

                SignedJWT signedJWT = SignedJWT.parse(jwtString);
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                claimsVerifier.verify(claims, null);
                signatureVerifier.verify(signedJWT);
                return this.extractMetadata(claims);
            }
        } catch (ParseException | BadJWTException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthenticationMetadata extractMetadata(JWTClaimsSet claims) throws InvalidBearerTokenException {

        JsonNode tokenData = this.tryParseTokenData(claims);
        this.validateTokenData(tokenData);

        try {
            return objectMapper.readValue(claims.getClaim("data").toString(),
                    AuthenticationMetadata.class);
        } catch (Exception var21) {
            throw new InvalidBearerTokenException("Couldn't parse JWT data", var21);
        }
    }

    private void validateTokenData(JsonNode tokenData) throws InvalidBearerTokenException {

        if (tokenData == null || tokenData.isNull()) {
            throw new InvalidBearerTokenException("No data object in JWT");
        }
    }

    private JsonNode tryParseTokenData(JWTClaimsSet claims) throws InvalidBearerTokenException {

        try {
            return objectMapper.readTree(claims.getClaim("data").toString());
        } catch (IOException var3) {
            throw new InvalidBearerTokenException("Couldn't parse data", var3);
        }
    }
}

package orchestrator.security.jwt.properties;


import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public class JwtSecurityProperties {

    private String issuer;
    private int tokenExpirationInMinutes;
    private Map<String, String> claims;
}

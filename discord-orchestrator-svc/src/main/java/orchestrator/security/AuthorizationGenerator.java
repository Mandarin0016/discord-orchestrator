package orchestrator.security;

import orchestrator.security.dto.UserAuthenticationDetails;

public interface AuthorizationGenerator {

    String generateAuthorizationHeader(UserAuthenticationDetails authenticationDetails);

}

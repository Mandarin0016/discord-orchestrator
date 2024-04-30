package orchestrator.security.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import orchestrator.security.jwt.AuthenticationJwtDecoder;
import orchestrator.security.jwt.auth.AuthenticationMetadata;
import orchestrator.user.exception.IllegalUserStateException;
import orchestrator.user.model.User;
import orchestrator.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class AccountStateInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthenticationJwtDecoder jwtDecoder;
    private final UserService userService;

    @Autowired
    public AccountStateInterceptor(AuthenticationJwtDecoder jwtDecoder, UserService userService) {
        this.jwtDecoder = jwtDecoder;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtToken = request.getHeader(AUTHORIZATION_HEADER);
        AuthenticationMetadata principleMetadata = jwtDecoder.decode(jwtToken);

        User user = userService.getById(UUID.fromString(principleMetadata.getUserId()), false);
        if (!user.isActive()) {
            log.warn(String.format("Rejected [%s] call to [\"%s\"] for inactive user with id=[%s].", request.getMethod(), request.getRequestURI(), principleMetadata.getUserId()));
            throw new IllegalUserStateException("Oops! You are not allowed to access this resource due to inactive account status.");
        }

        return true;
    }
}

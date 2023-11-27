package orchestrator.security.jwt.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import orchestrator.security.jwt.manager.AuthenticationJwtManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import orchestrator.security.jwt.auth.JwtAuthentication;

import java.io.IOException;

@Service
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    private final AuthenticationJwtManager authenticationJwtManager;

    public AuthenticationJwtFilter(AuthenticationJwtManager authenticationJwtManager) {
        this.authenticationJwtManager = authenticationJwtManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (jwt != null) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication(jwt);
            Authentication authentication = authenticationJwtManager.authenticate(jwtAuthentication);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}

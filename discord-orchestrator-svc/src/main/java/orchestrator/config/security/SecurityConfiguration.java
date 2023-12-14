package orchestrator.config.security;


import orchestrator.security.jwt.filter.AuthenticationJwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Clock;

import static orchestrator.api.Paths.BASE_PATH_V1;

@Configuration
@Import({
        RSAKeyConfiguration.class
})
public class SecurityConfiguration {

    private final AuthenticationJwtFilter authenticationJwtFilter;

    public SecurityConfiguration(AuthenticationJwtFilter authenticationJwtFilter) {
        this.authenticationJwtFilter = authenticationJwtFilter;
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST, BASE_PATH_V1 + "/register").permitAll()
                                .requestMatchers(BASE_PATH_V1 + "/login").hasAuthority("SUPER_ADMIN_PESHO"))
                .addFilterBefore(authenticationJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

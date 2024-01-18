package orchestrator.config.security;


import orchestrator.security.jwt.filter.AuthenticationJwtFilter;
import orchestrator.security.jwt.properties.JwtSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Clock;

import static orchestrator.api.Paths.BASE_PATH_V1;

@Import({
        RSAKeyConfiguration.class
})
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableConfigurationProperties({JwtSecurityProperties.class})
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
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST, BASE_PATH_V1 + "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, BASE_PATH_V1 + "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

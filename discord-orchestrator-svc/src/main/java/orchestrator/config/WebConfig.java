package orchestrator.config;

import orchestrator.security.interceptor.AccountStateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static orchestrator.config.security.SecurityConfiguration.LOGIN_URI;
import static orchestrator.config.security.SecurityConfiguration.REGISTER_URI;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final AccountStateInterceptor accountStateInterceptor;

    @Autowired
    public WebConfig(AccountStateInterceptor accountStateInterceptor) {
        this.accountStateInterceptor = accountStateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountStateInterceptor).excludePathPatterns(REGISTER_URI, LOGIN_URI);
    }
}

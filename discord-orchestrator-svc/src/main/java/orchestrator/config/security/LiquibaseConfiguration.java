package orchestrator.config.security;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({LiquibaseProperties.class})
public class LiquibaseConfiguration {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {

        SpringLiquibase liquibase = new SpringLiquibase();
        if(liquibaseProperties.isEnabled()) {
            liquibase.setShouldRun(true);
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
            liquibase.setContexts(liquibaseProperties.getContexts());
            liquibase.setDefaultSchema("discord_orchestrator_svc");
            liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        } else {
            liquibase.setShouldRun(false);
        }

        return liquibase;
    }
}
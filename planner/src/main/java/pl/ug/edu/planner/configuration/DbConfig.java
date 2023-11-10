package pl.ug.edu.planner.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/db.properties")
public class DbConfig {

    @Value("${db.database}")
    private String database;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;
}

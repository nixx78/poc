package lv.nixx.poc.first.config;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ActuatorConfig {

    @Bean
    public HealthIndicator alphaDsHealthIndicator(DataSource alphaDataSource) {
        return new DataSourceHealthIndicator(alphaDataSource, "select now()");
    }

}

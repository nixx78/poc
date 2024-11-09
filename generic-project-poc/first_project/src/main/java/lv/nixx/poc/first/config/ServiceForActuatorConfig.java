package lv.nixx.poc.first.config;

import lv.nixx.poc.first.service.ServiceForMonitoring;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceForActuatorConfig {

    @Bean
    public HealthIndicator serviceHealthIndicator(ServiceForMonitoring service) {
        return () -> Health.status(service.getStatus())
                .withDetail("detail", service.getDetails())
                .withDetail("message", service.getMessage())
                .build();
    }


}

package lv.nixx.poc.first.service;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class ExternalServiceHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder)  {
        builder.up()
                .withDetail("timestamp", System.currentTimeMillis())
                .withDetail("componentDescription", "ExternalService");
    }

}

package lv.nixx.poc.resilience.limit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lv.nixx.poc.resilience.ExternalService;
import org.junit.jupiter.api.Test;

import java.time.Duration;

//TODO https://resilience4j.readme.io/docs/ratelimiter
public class AcquirePermissionSandbox {

    private final ExternalService externalService = new ExternalService();

    private final RateLimiterConfig config = RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMinutes(1))
            .timeoutDuration(Duration.ofMillis(100))
            .limitForPeriod(2)
            .build();

    private final RateLimiter rateLimiter = RateLimiterRegistry.of(config).rateLimiter("externalSystemLimiter");

    @Test
    void acquirePermission() {

        process(10);
        process(20);
        process(30);

    }

    private void process(int data) {
        if (rateLimiter.acquirePermission()) {
            System.out.println("Process result:" + externalService.process(data));
        } else {
            System.out.println("Request not allowed");
        }
    }

}

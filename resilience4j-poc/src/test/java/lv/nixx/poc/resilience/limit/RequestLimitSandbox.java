package lv.nixx.poc.resilience.limit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lv.nixx.poc.resilience.ExternalService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Function;

//TODO https://resilience4j.readme.io/docs/ratelimiter
public class RequestLimitSandbox {

    private final ExternalService externalService = new ExternalService();

    @Test
    void decoratedFunctionSample() {

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(2)
                .build();

        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        RateLimiter rateLimiter = registry.rateLimiter("externalSystemLimiter");

        Function<Integer, String> decorated
                = RateLimiter.decorateFunction(rateLimiter, externalService::process);

        System.out.println(decorated.apply(10));
        System.out.println(decorated.apply(20));

        try {
            System.out.println(decorated.apply(30));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}

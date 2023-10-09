package lv.nixx.poc.ratelimit.service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnnotatedCalculatorService {

    private static final Logger log = LoggerFactory.getLogger(AnnotatedCalculatorService.class);

    @RateLimiter(name = "simpleRequest")
    public int calculate(int a, int b) {
        return a + b;
    }

    @RateLimiter(name = "adminRequest")
    public int calculateByAdmin(int a, int b) {
        return a + b;
    }

    @RateLimiter(name = "simpleRequest", fallbackMethod = "fallbackMethod")
    public int calculateWithFallback(int a, int b) {
        return a + b;
    }

    private int fallbackMethod(int a, int b, RequestNotPermitted requestNotPermitted) {
        log.info("Fallback method called a [{}] b [{}] exception message [{}]", a, b, requestNotPermitted.getMessage());

        return -1;
    }

}

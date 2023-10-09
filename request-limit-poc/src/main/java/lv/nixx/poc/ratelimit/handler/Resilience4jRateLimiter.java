package lv.nixx.poc.ratelimit.handler;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;


public class Resilience4jRateLimiter implements HandlerInterceptor {

    private final RateLimiterConfig config = RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMinutes(1))
            .timeoutDuration(Duration.ofMillis(100))
            .limitForPeriod(2)
            .build();

    final RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String user = request.getParameter("user");
        if (user == null) {
            throw new IllegalArgumentException("Parameter 'user' must be provided");
        }

        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(user);

        if (rateLimiter.acquirePermission()) {
            return true;
        } else {
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API Request Quota");
            return false;
        }
    }

}

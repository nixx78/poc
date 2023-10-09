package lv.nixx.poc.ratelimit.handler;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lv.nixx.poc.ratelimit.service.UserLimiterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;


public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);

    private final UserLimiterService userLimiterService = new UserLimiterService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String user = request.getParameter("user");
        if (user == null) {
            throw new IllegalArgumentException("Parameter 'user' must be provided");
        }

        Bucket bucketForUser = userLimiterService.resolveBucket(user);
        ConsumptionProbe probe = bucketForUser.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            long remainingTokens = probe.getRemainingTokens();
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(remainingTokens));

            log.info("For user [{}] remaining tokens [{}]", user, remainingTokens);

            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;

            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API Request Quota");
            return false;
        }

    }
}
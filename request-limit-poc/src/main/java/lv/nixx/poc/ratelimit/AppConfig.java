package lv.nixx.poc.ratelimit;

import lv.nixx.poc.ratelimit.handler.RateLimitInterceptor;
import lv.nixx.poc.ratelimit.handler.Resilience4jRateLimiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor interceptor = new RateLimitInterceptor();
    private final Resilience4jRateLimiter resilience4jRateLimiter = new Resilience4jRateLimiter();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/calculator_interceptor/**");

        registry.addInterceptor(resilience4jRateLimiter)
                .addPathPatterns("/resilience_calculator/**");

    }


}
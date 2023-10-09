### Проект с примером использования RateLimiter, используются библиотеки:

- bucket4j-core
- resilience4j-ratelimiter

Контролеры в приложение:
- CalculatorControllerWithEmbeddedLimiter - пример примитивного контроллера,ограничения на уровне метода
- CalculatorControllerForInterceptor - для ограничения используется interceptor на уровне URL
- CalculatorControllerForResilience
- ControllerForAnnotatedService - вызовы сервиса с @RateLimiter


- Resilience4jRateLimiter - пример использования RateLimiter на при помощи Resilience4j

Swagger endpoint:
http://localhost:8080/RateLimiterPoC/swagger-ui.html

Actuator endpoints для контроля rate limits:

- http://localhost:8080/RateLimiterPoC/actuator/health
- http://localhost:8080/RateLimiterPoC/actuator/metrics/resilience4j.ratelimiter.available.permissions
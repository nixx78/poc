### Проект с примером использования RateLimiter, используется библиотека: bucket4j-core

- CalculatorControllerWithEmbeddedLimiter - пример примитивного контроллера,ограничения на уровне метода
- CalculatorControllerForInterceptor - для ограничения используется interceptor на уровне URL

- Resilience4jRateLimiter - пример использования RateLimiter на при помощи Resilience4j

Swagger endpoint:
http://localhost:8080/RateLimiterPoC/swagger-ui.html
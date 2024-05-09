Проект песочница для исследования интеграции Micrometer & Actuator

**!!!** Для работы в Spring Context должен быть TimedAspect

В проекте, показаты примеры аннотаций:
 - @Timed
 - @Counted

Получить статитистику через WEB можно при помощи URL: http://localhost:8080/actuator/metrics/_service.process
**!!!** Статистика будет видна только после первого вызова endpoint

Enpoint для Prometheus: http://localhost:8080/actuator/prometheus


TODO:
- https://medium.com/simform-engineering/revolutionize-monitoring-empowering-spring-boot-applications-with-prometheus-and-grafana-e99c5c7248cf
# PoC Проект для изучения возможностей хранения общих конфигураций для нескольких проектов

## Проекты

### common-proj
Проект для хранения общих конфигураций

## libs-commons
Проект для хранения общих зависимостей

### first_project
Проект, который использует общие конфигурации
* Swagger: http://localhost:8080/first-project/swagger-ui.html

### second_project
Проект, который использует общие конфигурации
* Swagger: http://localhost:8081/second-project/swagger-ui.html

## Docker
При помощи docker создаются следующие артефакты

### Database
Пользователь и таблицы с данными инициализируются скриптами
* AlphaDB
* BetaDb
 
### Hazelcast
Hazelcast Management-center: http://localhost:7777/

## ToDo
* commons-proj -> config-commons ?

* createHealthIndicator -> Annotation property
* @LoggedUserController -> добавить настройку для контроля списка полей, который будет возвращать /service/login, настройка URL
* Аннотация, которая подключает настройки общие для всех проектов: @EnableCommonProperties -> загрузить из common.properties
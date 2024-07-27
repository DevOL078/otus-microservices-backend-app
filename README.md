# Backend-app
Сервис для хранения и управления данными пользователей.

### Use cases
- Создание пользователя
- Поиск пользователя по логину и паролю
- Получение данных пользователя по id (ограничено авторизацией)
- Изменение данных пользователя по id (ограничено авторизацией)
- Удаление данных пользователя по id (ограничено авторизацией)

Для запросов, ограниченных авторизацией, требуется JWT-токен в заголовке запроса _Authorization_.

При обработке запросов, ограниченных авторизацией, происходит проверка соответствия токена и id пользователя, с чьими данными выполняется действие.

### API
<span style="color:yellow">*TODO*</span>

### Deployment
Для запуска сервиса в k8s необходимо выполнить следующие команды из корня проекта:

1. Сборка артефакта
```
mvn clean package
```

2. Сборка Docker-образа сервиса
```
docker build . -t [image_name]:[image_tag]
docker push [image_name]:[image_tag]
```

3. Сборка Docker-образа со скриптами миграции
```
docker build ./src/main/resources/db -t [image_migration_name]:[image_migration_tag]
docker push [image_migration_name]:[image_migration_tag]
```

4. Установка helm-чарта с БД Postgres
   1. Создание секрета для БД (нужно заменить плейсхолдеры для паролей своими значениями)
    ```
    kubectl create secret generic postgresql-db-pass --from-literal=password='[app-password]' --from-literal=postgres-password='[postgres-password]'
    ```
   2. Установка helm-чарта 
    ```
    helm install postgresql oci://registry-1.docker.io/bitnamicharts/postgresql -f ./k8s/helm/postgresql-values.yaml
    ```

3. Установка helm-чарта приложения
(в файле [values.yaml](k8s%2Fhelm%2Fbackend-app%2Fvalues.yaml) необходимо прописать название образа и тег, которые будут использоваться при деплое -
и для сервиса, и для миграций)
```
helm upgrade --install backend-app ./k8s/helm/backend-app
```

### Helm-chart
1. Deployment - [deployment.yaml](k8s%2Fhelm%2Fbackend-app%2Ftemplates%2Fdeployment.yaml)
2. Service - [service.yaml](k8s%2Fhelm%2Fbackend-app%2Ftemplates%2Fservice.yaml)
3. ServiceMonitor - [service-monitor.yaml](k8s%2Fhelm%2Fbackend-app%2Ftemplates%2Fservice-monitor.yaml)
4. ConfigMap - [config-map.yaml](k8s%2Fhelm%2Fbackend-app%2Ftemplates%2Fconfig-map.yaml)
5. Job - [job-migration.yaml](k8s%2Fhelm%2Fbackend-app%2Ftemplates%2Fjob-migration.yaml)

Values - [values.yaml](k8s%2Fhelm%2Fbackend-app%2Fvalues.yaml)

### Liveness probe
```
GET http://[host:port]/health
```
Response (200):
```
{
    "status": "OK"
}
```


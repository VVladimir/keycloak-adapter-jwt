# keycloak-adapter-jwt

Cервис авторизации через keycloak


запускаем docker-compose.yml

POST http://localhost:8484/auth/realms/my_realm/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=my_client&grant_type=password&scope=openid&username=admin&password=admin




###

### доступ пользователю с ролью ADMIN
GET http://localhost:8080/api/admin
Authorization: Bearer {{auth_token}}
Content-Type: application/json

### доступ пользователю с ролью USER
GET http://localhost:8080/api/user
Authorization: Bearer {{auth_token}}
Content-Type: application/json

### информацию о пользователе
GET http://localhost:8080/api/me
Authorization: Bearer {{auth_token}}
Content-Type: application/json

### темы для страницы авторизации
https://github.com/InseeFrLab/keycloakify

### бенчмарки
бенчмарки
1. https://github.com/fabric8-services/keycloak-performance-scripts
2. https://github.com/keycloak/keycloak-benchmark

### Keycloak - сообщество на tlegram
https://t.me/keycloak_ru
version: "3.8"

services:
  postgres:
    container_name: postgres
    image: library/postgres
    environment:
      POSTGRES_USER: ${POSTGRES_USER:-postgres}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD:-postgres}
      POSTGRES_DB: keycloak_db
    ports:
      - "5432:5432"
    restart: unless-stopped

  keycloak:
    image: jboss/keycloak
    container_name: keycloak
    environment:
      DB_VENDOR: POSTGRES
      DB_ADDR: postgres
      DB_DATABASE: keycloak_db
      DB_USER: ${POSTGRES_USER:-postgres}
      DB_PASSWORD: ${POSTGRES_PASSWORD:-postgres}
      KEYCLOAK_USER: admin
      KEYCLOAK_PASSWORD: admin
    ports:
      - "8484:8080"
    depends_on:
      - postgres
    links:
      - "postgres:postgres"
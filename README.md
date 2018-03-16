# Remote-API-Web-Reference-for-Java-Enterprise-Applications

Install:
v top adresari staci mvn clean install

Zprovozneni db
1. createdb -h localhost -p 5433 -U postgres <nazevdb>
2. v application.properties nastavit generate-schema=update

Spusteni
BACKEND:
v backend adresari mvn spring-boot:run

FRONTEND:
v frontend/src/main/frontend npm start
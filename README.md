# Remote-API-Web-Reference-for-Java-Enterprise-Applications

Install:
1. Download and install the JDK 1.8
2. Download and install Apache Maven 3
3. Download lastest NodeJS
4. If on Windows, add Java and Maven to system variables
5. In the root folder of the project (./restty-app) run the command "mvn clean install"
6. Install the angular-cli using the command "npm install -g @angular-cli"

Startup:
a) Server side - run the "mvn spring-boot:run" command in the "./restty-app/backend" folder.
b) Client side - run the "ng serve --proxy-conf proxy.conf.json" command in the "./restty-app/frontend/src/main/frontend" folder
FROM openjdk:17-jdk-alpine
COPY target/inventory-0.0.1-SNAPSHOT.jar inventory_app.jar
ENTRYPOINT ["java","-jar","/inventory_app.jar"]


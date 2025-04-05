FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/deliveries-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "deliveries-service-0.0.1-SNAPSHOT.jar"]
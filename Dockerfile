FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

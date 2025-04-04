FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

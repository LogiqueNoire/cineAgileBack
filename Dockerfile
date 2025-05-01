FROM maven:3-amazoncorretto-21 AS builder
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]
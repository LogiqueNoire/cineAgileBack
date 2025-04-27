FROM amazoncorretto:21
WORKDIR /app
COPY /target/CineAgile-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-Dspring.profiles.active=docker", "-jar", "app.jar" ]
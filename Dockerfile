FROM eclipse-temurin:21-alpine
COPY target/shareit-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "shareit-0.0.1-SNAPSHOT.jar"]

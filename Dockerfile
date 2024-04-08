FROM openjdk:17-jdk-alpine
ARG JAR_FILE="target/backend-app-0.0.1-SNAPSHOT.jar"
WORKDIR /opt/app
COPY ${JAR_FILE} backend-app.jar
ENTRYPOINT ["java", "-jar", "backend-app.jar"]
EXPOSE 8000
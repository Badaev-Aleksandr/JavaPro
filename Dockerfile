FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/development-1.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
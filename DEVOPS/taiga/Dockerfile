FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD taiga-0.0.1-SNAPSHOT.jar taiga-microservice.jar
ENTRYPOINT ["java", "-jar", "/taiga-microservice.jar", "--spring.profiles.active=dev"]

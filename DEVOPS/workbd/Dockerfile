FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD WorkBD-0.0.1-SNAPSHOT.jar workbd-microservice.jar
ENTRYPOINT ["java", "-jar", "/workbd-microservice.jar", "--spring.profiles.active=dev"]

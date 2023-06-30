FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD PWD-0.0.1-SNAPSHOT.jar pwd-microservice.jar
ENTRYPOINT ["java", "-jar", "/pwd-microservice.jar", "--spring.profiles.active=dev"]

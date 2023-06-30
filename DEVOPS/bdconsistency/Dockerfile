FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD BDConsistency-0.0.1-SNAPSHOT.jar bdc-microservice.jar
ENTRYPOINT ["java", "-jar", "/bdc-microservice.jar", "--spring.profiles.active=dev"]

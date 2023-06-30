FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD SBCoupling-0.0.1-SNAPSHOT.jar sbcoupling-microservice.jar
ENTRYPOINT ["java", "-jar", "/sbcoupling-microservice.jar", "--spring.profiles.active=dev"]

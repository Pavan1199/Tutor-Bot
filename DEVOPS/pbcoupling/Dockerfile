FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD PBCoupling-0.0.1-SNAPSHOT.jar pbcoupling-microservice.jar
ENTRYPOINT ["java", "-jar", "/pbcoupling-microservice.jar", "--spring.profiles.active=dev"]

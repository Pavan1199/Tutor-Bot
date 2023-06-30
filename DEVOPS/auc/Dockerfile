FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD AUC-0.0.1-SNAPSHOT.jar auc-microservice.jar
ENTRYPOINT ["java", "-jar", "/auc-microservice.jar", "--spring.profiles.active=dev"]

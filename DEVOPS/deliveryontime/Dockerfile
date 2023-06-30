FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD DeliveryOnTime-0.0.1-SNAPSHOT.jar deliveryontime-microservice.jar
ENTRYPOINT ["java", "-jar", "/deliveryontime-microservice.jar", "--spring.profiles.active=dev"]

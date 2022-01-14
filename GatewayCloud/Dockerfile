FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-docker-gateway.jar spring-boot-docker-gateway.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker-gateway.jar"]
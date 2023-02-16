FROM openjdk:17-oracle
EXPOSE 9090
ADD target/Cloud_service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
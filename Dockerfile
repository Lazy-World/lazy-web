FROM openjdk:11-oracle
COPY target/*.jar messenger.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "messenger.jar"]
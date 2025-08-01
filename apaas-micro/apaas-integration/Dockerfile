FROM openjdk:11-jre-slim

VOLUME /tmp

COPY target/apaas-integration-1.0.0.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/importer-0.0.1-SNAPSHOT.jar importer.jar
ENTRYPOINT ["java","-jar","importer.jar"]

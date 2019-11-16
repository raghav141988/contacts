FROM openjdk:8-jdk-alpine
VOLUME /tmp
# ARG JAR_FILE
WORKDIR /target
COPY contacts-backend.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
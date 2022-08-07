#FROM openjdk:11-jdk-oracle
FROM maven:latest
RUN groupadd spring && useradd -g spring spring
USER spring:spring
ARG WORK_DIR=.
CMD ["mvn", "install"]
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
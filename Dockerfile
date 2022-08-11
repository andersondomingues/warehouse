# stage 1 : build
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Daws_key=production", "-Daws_secret=production", "-Daws_bin=test", "-jar","/usr/local/lib/app.jar" ]
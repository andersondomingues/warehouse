# stage 1 : build
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Daws_bin_test=database/test", "-Daws_key_test=AKIAQ2CZVFGCT4YUUOU6", "-Daws_secret_test=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", "-Daws_bin_prod=database/test", "-Daws_key_prod=AKIAQ2CZVFGCT4YUUOU6", "-Daws_secret_prod=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", "-jar","/usr/local/lib/app.jar" ]
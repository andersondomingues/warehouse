# stage 1 : build
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar", "-Dhibernate_db_user=\"postgres\"", "-Dhibernate_db_pass=\"CED3rnHDra9I5QlcBUCU\"", "-Dhibernate_db_host=\"database-warehouse-prod.cwd25dfzkdvu.sa-east-1.rds.amazonaws.com\"", "-Dhibernate_db_name=\"warehouse\""]
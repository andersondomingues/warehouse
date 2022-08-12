# stage 1 : build
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM amazoncorretto
ARG aws_bin_test
ARG aws_key_test
ARG aws_secret_test
ARG aws_bin_prod
ARG aws_key_prod
ARG aws_secret_prod
COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
#RUN ENTRYPOINT ["java", ${aws_bin_test}, ${aws_key_test}, ${aws_secret_test}, ${aws_bin_prod}, ${aws_key_prod}, ${aws_secret_prod}, "-jar","/usr/local/lib/app.jar" ]
CMD java ${aws_bin_test} ${aws_key_test} ${aws_secret_test} ${aws_bin_prod} ${aws_key_prod} ${aws_secret_prod} -jar /usr/local/lib/app.jar
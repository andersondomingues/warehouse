# stage 1 : build
FROM maven:3-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM amazoncorretto:17
ARG aws_bin_test
ARG aws_key_test
ARG aws_secret_test
ARG aws_bin_prod
ARG aws_key_prod
ARG aws_secret_prod

# persisting env vars from arg
ENV eaws_bin_test=${aws_bin_test}
ENV eaws_key_test=${aws_key_test}
ENV eaws_secret_test=${aws_secret_test}
ENV eaws_bin_prod=${aws_bin_prod}
ENV eaws_key_prod=${aws_key_prod}
ENV eaws_secret_prod=${aws_secret_prod}

COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
CMD java $eaws_bin_test $eaws_key_test $eaws_secret_test $eaws_bin_prod $eaws_key_prod $eaws_secret_prod -jar /usr/local/lib/app.jar
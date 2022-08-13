# stage 1 : build
FROM maven:3-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# stage 2: run
FROM amazoncorretto:17
ARG aws_bin
ARG aws_key
ARG aws_secret

# persisting env vars from arg
ENV eaws_bin=${aws_bin}
ENV eaws_key=${aws_key}
ENV eaws_secret=${aws_secret}

COPY --from=build /home/app/target/warehouse-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
CMD java $eaws_bin $eaws_key $eaws_secret -jar /usr/local/lib/app.jar
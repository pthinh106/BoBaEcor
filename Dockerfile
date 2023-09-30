#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
#
#
# Package stage$ docker build -t docker-apache2 .
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/BoBaEcor-0.0.1-SNAPSHOT.jar BoBaEcor.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","BoBaEcor.jar"]

FROM maven:3.8.6-amazoncorretto-11 AS builder

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
# Separate the dependency download from the build to leverage Docker caching
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn clean package

FROM openjdk:11-jre
COPY --from=builder /usr/app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
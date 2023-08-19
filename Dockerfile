FROM maven:3.9.4 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn package

FROM adoptopenjdk/openjdk17:alpine-jre
ARG JAR_FILE=itx-technical-test.jar

WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","spring-boot-api-tutorial.jar"]
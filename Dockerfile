FROM maven:3.8.1-jdk-8 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/product-sale-0.0.1-SNAPSHOT.jar /${WORKDIR}/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
CMD ["sh build"]
ARG JAR_FILE=target/product-sale-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
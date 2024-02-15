FROM openjdk:17-jdk

WORKDIR /app
RUN ls -la
COPY build/libs/bid-core-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8083
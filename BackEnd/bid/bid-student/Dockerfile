FROM openjdk:17-jdk

WORKDIR /app
COPY build/libs/bid-student-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8082
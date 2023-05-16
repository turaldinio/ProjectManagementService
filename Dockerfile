FROM openjdk:18.0.2.1-slim-buster

EXPOSE 8081

COPY target/pm-sample-exec.jar ./

CMD  ["java", "-jar", "pm-sample-exec.jar"]
FROM openjdk:18.0.2.1-slim-buster

EXPOSE 8081

COPY target/management_system-sample-exec.jar ./

CMD  ["java", "-jar", "management_system-sample-exec.jar"]
FROM openjdk:17-jdk-slim

COPY target/demo-0.0.1-SNAPSHOT.jar /d424_jcs.war

EXPOSE 5000

CMD ["java", "-jar", "/d424_jcs.war"]

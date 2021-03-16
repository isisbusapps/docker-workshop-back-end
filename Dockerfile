ARG WINDOWS_VERSION
FROM docker-registry.devs.facilities.rl.ac.uk/bisapps-maven:3.6-jdk8-servercore${WINDOWS_VERSION}-1.0.0-SNAPSHOT

COPY pom.xml .
COPY src src

RUN mvn clean install

CMD ["mvn.cmd", "exec:java"]

FROM maven:3.6.3-jdk-8-slim AS build
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml verify clean --fail-never
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/biblio-0.0.1-SNAPSHOT.jar /usr/local/lib/biblio.jar
EXPOSE 8080
CMD ["java","-jar","/usr/local/lib/biblio.jar"]
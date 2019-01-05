FROM openjdk:8-jre-alpine

COPY target/explore-rest-api-1.0-SNAPSHOT.jar /home/explore-rest-api.jar

COPY target/lib /home/lib/

WORKDIR /home

RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 9010

ENTRYPOINT ["java", "-jar", "explore-rest-api.jar"]
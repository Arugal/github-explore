FROM openjdk:8-jre-alpine

COPY target/explore-spider-1.0-SNAPSHOT.jar /home/spider.jar

COPY target/lib /home/lib/

WORKDIR /home

RUN echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT ["java", "-jar", "spider.jar"]
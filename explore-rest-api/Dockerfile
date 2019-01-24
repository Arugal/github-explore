FROM hengyunabc/arthas

COPY target/explore-rest-api-1.0-SNAPSHOT.jar /home/explore-rest-api.jar

COPY target/lib /home/lib/

COPY docker-entrypoint.sh /home/docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

WORKDIR /home

RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 9010

EXPOSE 5005

ENTRYPOINT ["/home/docker-entrypoint.sh", "explore-rest-api.jar"]
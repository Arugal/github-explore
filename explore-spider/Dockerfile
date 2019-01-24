FROM hengyunabc/arthas

COPY target/explore-spider-1.0-SNAPSHOT.jar /home/explore-spider.jar

COPY target/lib /home/lib/

COPY docker-entrypoint.sh /home/docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 5006

WORKDIR /home

RUN echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT ["/home/docker-entrypoint.sh", "explore-spider.jar"]
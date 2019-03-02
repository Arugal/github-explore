#!/bin/sh

docker run -d --name explore-mysql -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.25

docker cp ./mysql.cnf explore-mysql:/etc/mysql/conf.d/ && \
    docker restart explore-mysql
#!/bin/sh

docker stop explore-web
docker rm explore-web 
docker rmi explore-web:1.0
rm -rf dist && tar -zxvf dist.tar.gz
docker build -t explore-web:1.0 . 
docker run -d --name explore-web -p 80:80 explore-web:1.0

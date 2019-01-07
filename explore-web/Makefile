install:
	@npm install
dev: install
	@npm run dev
build:
	@npm run build
deploy: build nowtime
	tar -zcvf dist.tar.gz dist &&\
		scp -r ./dist.tar.gz root@dell.com:/root/explore/web/ &&\
		scp Dockerfile root@dell.com:/root/explore/web/ &&\
		scp docker-deploy.sh root@dell.com:/root/explore/web/ &&\
		scp nowtime root@dell.com:/root/explore/web/ &&\
		rm dist.tar.gz && \
		rm nowtime

nowtime:
	./nowtime.sh
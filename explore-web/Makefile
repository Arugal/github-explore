install:
	npm install
dev: install
	npm run dev

build_web:
	rm -rf dist && \
		npm run build

deploy: nowtime package
		tar -zcvf dist.tar.gz dist &&\
		scp -r ./dist.tar.gz root@dell.com:/root/explore/web/ &&\
		scp Dockerfile root@dell.com:/root/explore/web/ &&\
		scp docker-deploy.sh root@dell.com:/root/explore/web/ &&\
		scp nowtime root@dell.com:/root/explore/web/ &&\
		rm dist.tar.gz && \
		rm nowtime

package: build_web
	rm dist.tar.gz && \
		tar -zcvf dist.tar.gz dist
		
nowtime:
	./nowtime.sh
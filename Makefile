clean:
	@mvn clean
install: clean
	@mvn install -Dmaven.test.skip=true

all: clean install

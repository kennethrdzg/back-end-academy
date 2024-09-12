default: run

clean: 
	cd smalltalk-server; mvn clean;
build: 
	cd smalltalk-server; mvn compile;
run: 
	cd smalltalk-server; mvn spring-boot:run;

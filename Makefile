default: run

clean: 
	cd server; mvn clean;
build: 
	cd server; mvn compile;
run: 
	cd server; mvn spring-boot:run;

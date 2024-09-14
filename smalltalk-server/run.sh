#! /bin/bash

source env_variables.sh
mvn clean compile test
mvn spring-boot:run


#!/bin/bash -eu

docker-compose run --rm maven mvn install -DskipTests=true -Dmaven.javadoc.skip=true -e
# docker-compose run --rm maven mvn clean test site -e
docker-compose run --rm maven mvn clean package -e

mkdir target/webapps
mv target/knowledge.war target/webapps/ROOT.war

docker-compose up --build -d tomcat
docker-compose logs -f tomcat

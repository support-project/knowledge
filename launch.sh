#!/bin/bash -eu

docker-compose run --rm maven mvn install -DskipTests=true -Dmaven.javadoc.skip=true -e
# docker-compose run --rm maven mvn clean test site -e
docker-compose run --rm maven mvn clean package -e

mv target/knowledge.war target/ROOT.war

docker-compose run --rm knowledge rm -rf /usr/local/tomcat/webapps/*
docker-compose run --rm knowledge ls /usr/local/tomcat/webapps
docker-compose up --build -d knowledge
docker-compose logs -f knowledge

#!/bin/bash -eu

mkdir -p third_party
git clone --branch v1-dev https://github.com/y-okumura-isp/markedj.git third_party/markedj

docker-compose run --rm maven sh -c "cd third_party/markedj; mvn install -DskipTests=true -Dmaven.javadoc.skip=true"
docker-compose run --rm maven mvn install -DskipTests=true -Dmaven.javadoc.skip=true -e
# docker-compose run --rm maven mvn clean test site -e
docker-compose run --rm maven mvn clean package -e

mkdir target/webapps
mv target/knowledge.war target/webapps/ROOT.war

docker-compose up --build -d tomcat
docker-compose logs -f tomcat

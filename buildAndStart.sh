#!/bin/bash
docker-compose run --rm frontend npm run build
cp -r frontend/dist/* backend/src/main/webapp
docker-compose run --rm maven mvn clean package -DskipTests=true
docker-compose up --build -d tomcat
docker-compose logs -f tomcat

docker-compose run --rm frontend npm run build
cp -R frontend/dist/ backend/src/main/webapp/
docker-compose run --rm maven mvn clean package
docker-compose run --rm -p 8080:8080 tomcat


#!/bin/bash -eu

propagate_proxy_vars () {
  # For newer JVM versions, use this instead.
  # echo " -Djava.net.useSystemProxies=true"

  if test -n "$https_proxy"; then
    echo " -Dhttps.proxyHost=$(echo $https_proxy | sed 's|.*://||; s|:.*||')"
    echo " -Dhttps.proxyPort=$(echo $https_proxy | sed 's|.*:||')"
  fi
  if test -n "$http_proxy"; then
    echo " -Dhttp.proxyHost=$(echo $http_proxy | sed 's|.*://||; s|:.*||')"
    echo " -Dhttp.proxyPort=$(echo $http_proxy | sed 's|.*:||')"
  fi
  if test -n "$no_proxy"; then
    echo " -Dhttp.nonProxyHosts=$(echo $no_proxy | sed 's/,\./,*./g; s/,/|/g')"
  fi
}

docker-compose run --rm maven mvn install -DskipTests=true -Dmaven.javadoc.skip=true -e $(propagate_proxy_vars)
# docker-compose run --rm maven mvn clean test site -e $(propagate_proxy_vars)
docker-compose run --rm maven mvn clean package -e $(propagate_proxy_vars)

# Run everything through docker-compose to work around permission issues.
docker-compose run --rm maven mkdir target/webapps
docker-compose run --rm maven mv target/knowledge.war target/webapps/ROOT.war

docker-compose up --build -d tomcat
docker-compose logs -f tomcat

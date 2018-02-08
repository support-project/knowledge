FROM maven:3.5-jdk-8-slim

# install git
RUN apt-get -y update && apt-get -y install git
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /usr/share/man/?? /usr/share/man/??_*

WORKDIR /usr/src/mymaven/
ENTRYPOINT []

VOLUME /root/.m2



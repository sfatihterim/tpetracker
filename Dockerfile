FROM openjdk:8-jdk

WORKDIR /srv/
ADD . /srv/
CMD [ "./gradlew", "bootrun" ]

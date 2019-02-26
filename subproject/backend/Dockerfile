FROM openjdk:8u191-jre-alpine3.9
ARG jar=build/libs/service.jar
EXPOSE 8080
RUN apk update && apk upgrade
COPY $jar /srv/service.jar
RUN chmod 0644 /srv/service.jar
RUN adduser -D -g service service
CMD [ "su", "service", "-c", "java -jar /srv/service.jar" ]

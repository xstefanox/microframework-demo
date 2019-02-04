FROM openjdk:8u191-jre-alpine3.8
EXPOSE 8080
RUN apk update && apk upgrade
COPY build/libs/service.jar /srv
RUN chmod 0644 /srv/service.jar
RUN adduser -D -g service service
CMD [ "su", "service", "-c", "java -jar /srv/service.jar" ]

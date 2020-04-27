FROM openjdk:11
COPY ./build/libs/library-app-0.0.1-SNAPSHOT.jar /usr/app/library-app.jar
COPY ./config/docker/application.yml /usr/app/
WORKDIR /usr/app
EXPOSE 8080
CMD java -jar library-app.jar --spring.config.location=file:/usr/app/application.yml
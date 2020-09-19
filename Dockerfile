FROM openjdk:8-jdk-alpine AS TEMP_BUILD_IMAGE
MAINTAINER Oluwatobi Adenekan <oluwatobi.t.adenekan@gmail.com>
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew gradle.properties $APP_HOME/
#COPY gradle $APP_HOME/gradle
#RUN ./gradlew build
COPY build/libs/account-managment-0.1.0.jar $APP_HOME/build/libs/account-managment-0.1.0.jar
#RUN ./gradlew build
FROM openjdk:8-jdk-alpine
ENV ARTIFACT_NAME=account-managment-0.1.0.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD ["java","-jar", "account-managment-0.1.0.jar"]
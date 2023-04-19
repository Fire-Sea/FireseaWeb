FROM gradle:6-jdk11-alpine as builder
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
RUN chmod +x gradlew
RUN ./gradlew build || return 0
COPY src $APP_HOME/src
RUN ./gradlew clean compileQuerydsl
RUN ./gradlew clean build -x test

FROM adoptopenjdk/openjdk11
ENV APP_HOME=/apps
ARG ARTIFACT_NAME=app.jar
ARG JAR_FILE_PATH=build/libs/test-server-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME
#COPY --from=build /apps/build/libs/test-server-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder $APP_HOME/$JAR_FILE_PATH $ARTIFACT_NAME

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

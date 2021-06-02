FROM openjdk:11 as BUILDER
COPY . /usr/local/src/reactioner
WORKDIR /usr/local/src/reactioner

RUN chmod +x ./gradlew
RUN ./gradlew shadowJar

FROM alpine:latest
RUN apk add openjdk11-jre

COPY --from=BUILDER /usr/local/src/reactioner/build/libs/*.jar /app/reactioner.jar
RUN chmod +x /app/reactioner.jar

ENV IS_CONTAINER=TRUE

CMD [ "java", "-jar", "/app/reactioner.jar" ]
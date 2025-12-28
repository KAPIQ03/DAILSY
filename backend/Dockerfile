FROM amazoncorretto:21-alpine-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=build /app/build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
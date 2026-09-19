FROM eclipse-temurin:21-jdk-alpine AS cache
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY settings.gradle build.gradle ./

RUN chmod +x gradlew
RUN ./gradlew --version --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

RUN apk add --no-cache curl

COPY --from=cache /root/.gradle /root/.gradle
COPY --from=cache /app/gradlew /app/gradle /app/

COPY . .

RUN chmod +x gradlew
ENTRYPOINT ["./gradlew"]
CMD ["--help"]
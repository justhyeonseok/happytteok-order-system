# 1단계: Gradle 빌드 환경을 설정합니다.
FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN gradle bootJar

# 2단계: 가볍고 최종적인 런타임 환경을 만듭니다.
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
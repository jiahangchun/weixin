FROM openjdk:17-jdk-alpine

# docker build --build-arg SNAPSHOT_VERSION=0.0.2-SNAPSHOT -t myapp:latest .
# 通過git action 指定版本號
ARG VERSION=1.0.0-SNAPSHOT
RUN mkdir "/app"
WORKDIR /app
COPY ./target/demo-${SNAPSHOT_VERSION}-SNAPSHOT.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]

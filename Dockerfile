FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/myapp.jar /app
CMD ["java", "-jar", "myapp.jar"]

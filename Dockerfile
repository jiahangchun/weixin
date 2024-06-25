FROM openjdk:17-jdk
MAINTAINER jiahangchun

#对时作用
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# docker build --build-arg SNAPSHOT_VERSION=0.0.2-SNAPSHOT -t myapp:latest .
ARG PORT=8080


RUN echo "用户 ""$USER"" 打包文件"
ADD target/*.jar /app.jar
#查看是否存在app.jar文件
RUN bash -c 'touch /app.jar'
RUN chmod 777 /app.jar

EXPOSE ${PORT}
CMD ["java","-jar","/app.jar"]

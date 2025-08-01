# 使用OpenJDK 11作为基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 将jar包复制到容器中
COPY target/apaas-flow-execution.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
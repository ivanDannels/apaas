# APaaS 微服务项目

## 项目结构

- `apaas-integration`: 集成服务
- `apaas-job`: 任务服务
- `apaas-monitor`: 监控服务

## 构建

```bash
mvn clean package
```

## 运行

使用 Docker Compose 运行所有服务：

```bash
docker-compose up
```

## 服务端口

- apaas-integration: http://localhost:8081
- apaas-job: http://localhost:8082
- apaas-monitor: http://localhost:8083
- nacos: http://localhost:8848
# APaaS Job Service

## 构建

```bash
mvn clean package
```

## 运行

```bash
java -jar target/apaas-job-1.0.0.jar
```

## Docker

```bash
docker build -t apaas-job .
docker run -p 8082:8080 apaas-job
```
# APaaS Integration Service

## 构建

```bash
mvn clean package
```

## 运行

```bash
java -jar target/apaas-integration-1.0.0.jar
```

## Docker

```bash
docker build -t apaas-integration .
docker run -p 8081:8080 apaas-integration
```
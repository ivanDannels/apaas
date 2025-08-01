# APaaS Monitor Service

## 构建

```bash
mvn clean package
```

## 运行

```bash
java -jar target/apaas-monitor-1.0.0.jar
```

## Docker

```bash
docker build -t apaas-monitor .
docker run -p 8083:8080 apaas-monitor
```
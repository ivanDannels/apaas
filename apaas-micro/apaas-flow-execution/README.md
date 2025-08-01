# 流程执行服务 (Flow Execution Service)

## 服务概述

流程执行服务是APaaS平台的核心服务之一，负责流程实例的运行时管理、活动实例执行和工作项处理。

## 核心功能

1. **流程实例管理**
   - 启动流程实例
   - 终止流程实例
   - 查询流程实例状态

2. **活动实例管理**
   - 执行活动实例
   - 完成活动实例
   - 查询活动实例状态

3. **任务管理**
   - 查询用户任务
   - 领取任务
   - 完成任务
   - 转办任务

## 技术架构

- 基于Spring Boot WebFlux的响应式编程模型
- 使用PostgreSQL作为主数据存储
- 集成Redis用于缓存
- 使用RabbitMQ进行异步消息处理
- 通过MyBatis Plus简化数据访问层开发

## API接口

### 流程实例接口

- `POST /flow-execution/instances/start` - 启动流程实例
- `POST /flow-execution/instances/{flowInstanceId}/terminate` - 终止流程实例

### 活动实例接口

- `POST /flow-execution/activities/{activityInstanceId}/complete` - 完成活动实例

### 任务管理接口

- `GET /flow-execution/tasks` - 查询用户任务
- `POST /flow-execution/tasks/{taskId}/claim` - 领取任务
- `POST /flow-execution/tasks/{taskId}/complete` - 完成任务
- `POST /flow-execution/tasks/{taskId}/transfer` - 转办任务

## 数据库设计

1. `flow_instance` - 流程实例表
2. `activity_instance` - 活动实例表
3. `workflow_task` - 工作项表

## 部署说明

```bash
# 编译打包
mvn clean package

# 运行服务
java -jar apaas-flow-execution.jar
```
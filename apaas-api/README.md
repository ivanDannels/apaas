# APaaS API 模块说明

## 模块结构

apaas-api 模块包含以下子模块：

1. **apaas-core**：公共 API 组件

   - 包含所有 API 模块共用的基础类、工具类和常量

2. **apaas-inner-api**：内部 API

   - 供平台内部服务调用的 API 接口
   - 包含流程引擎、表单引擎等核心服务的 API 定义

3. **apaas-open-api**：开放 API
   - 供外部系统调用的 API 接口
   - 包含任务调度、报表服务等对外服务的 API 定义

## 核心组件

### 公共组件 (apaas-core)

- **ApiResponse**：统一 API 响应格式
- **BasePageRequest**：统一分页请求参数
- **PageResult**：统一分页结果格式
- **ApiException**：统一 API 异常

### 内部 API (apaas-inner-api)

#### 流程引擎服务

- **ProcessEngineApi**：流程引擎核心 API 接口
- **ProcessDefinitionDTO**：流程定义数据模型
- **ProcessInstanceDTO**：流程实例数据模型
- **ProcessTaskDTO**：流程任务数据模型

### 开放 API (apaas-open-api)

#### 任务调度服务

- **TaskSchedulerApi**：任务调度核心 API 接口
- **ScheduleTaskDTO**：调度任务数据模型
- **TaskExecutionLogDTO**：任务执行日志数据模型

## 使用指南

### 依赖引入

在需要使用 API 模块的项目中，添加以下依赖：

```xml
<dependency>
    <groupId>org.apaas</groupId>
    <artifactId>apaas-core</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>org.apaas</groupId>
    <artifactId>apaas-inner-api</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>org.apaas</groupId>
    <artifactId>apaas-open-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 接口调用示例

```java
// 调用流程引擎API示例
@Autowired
private ProcessEngineApi processEngineApi;

public void startProcess() {
    ProcessInstanceDTO instanceDTO = new ProcessInstanceDTO();
    instanceDTO.setDefinitionId("process-123");
    instanceDTO.setBusinessKey("biz-456");
    // 设置其他参数...

    ApiResponse<ProcessInstanceDTO> response = processEngineApi.startProcessInstance(instanceDTO);
    if (response.getCode() == 200) {
        ProcessInstanceDTO result = response.getData();
        // 处理结果...
    } else {
        // 处理异常...
    }
}
```

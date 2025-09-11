# 统一智能数据应用平台(UDAP) - API接口规范设计

## 1. 大数据平台API接口规范

### 1.1 数据源管理API

#### 1.1.1 创建数据源
- **接口路径**: POST /api/v1/datasources
- **请求参数**:
  ```json
  {
    "dsCode": "string",
    "dsName": "string",
    "dsType": "string",
    "connectionUrl": "string",
    "username": "string",
    "password": "string",
    "configJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "dsCode": "string",
    "dsName": "string",
    "dsType": "string",
    "status": "string",
    "createdTime": "datetime"
  }
  ```

#### 1.1.2 查询数据源列表
- **接口路径**: GET /api/v1/datasources
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  dsType: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "dsCode": "string",
        "dsName": "string",
        "dsType": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 1.1.3 获取数据源详情
- **接口路径**: GET /api/v1/datasources/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "dsCode": "string",
    "dsName": "string",
    "dsType": "string",
    "connectionUrl": "string",
    "username": "string",
    "status": "string",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 1.1.4 更新数据源
- **接口路径**: PUT /api/v1/datasources/{id}
- **请求参数**:
  ```json
  {
    "dsName": "string",
    "connectionUrl": "string",
    "username": "string",
    "password": "string",
    "configJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "dsCode": "string",
    "dsName": "string",
    "dsType": "string",
    "status": "string",
    "updatedTime": "datetime"
  }
  ```

#### 1.1.5 删除数据源
- **接口路径**: DELETE /api/v1/datasources/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

### 1.2 数据同步任务管理API

#### 1.2.1 创建同步任务
- **接口路径**: POST /api/v1/sync-tasks
- **请求参数**:
  ```json
  {
    "taskCode": "string",
    "taskName": "string",
    "sourceDsId": "string",
    "targetDsId": "string",
    "syncType": "string",
    "syncStrategy": "string",
    "tableMappingJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "taskCode": "string",
    "taskName": "string",
    "status": "string",
    "createdTime": "datetime"
  }
  ```

#### 1.2.2 查询同步任务列表
- **接口路径**: GET /api/v1/sync-tasks
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  status: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "taskCode": "string",
        "taskName": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 1.2.3 获取同步任务详情
- **接口路径**: GET /api/v1/sync-tasks/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "taskCode": "string",
    "taskName": "string",
    "sourceDsId": "string",
    "targetDsId": "string",
    "syncType": "string",
    "syncStrategy": "string",
    "tableMappingJson": "object",
    "status": "string",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 1.2.4 更新同步任务
- **接口路径**: PUT /api/v1/sync-tasks/{id}
- **请求参数**:
  ```json
  {
    "taskName": "string",
    "sourceDsId": "string",
    "targetDsId": "string",
    "syncType": "string",
    "syncStrategy": "string",
    "tableMappingJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "taskCode": "string",
    "taskName": "string",
    "status": "string",
    "updatedTime": "datetime"
  }
  ```

#### 1.2.5 删除同步任务
- **接口路径**: DELETE /api/v1/sync-tasks/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

#### 1.2.6 执行同步任务
- **接口路径**: POST /api/v1/sync-tasks/{id}/execute
- **响应参数**:
  ```json
  {
    "taskId": "string",
    "executionId": "string",
    "status": "string",
    "startTime": "datetime"
  }
  ```

### 1.3 数据资产管理API

#### 1.3.1 注册数据资产
- **接口路径**: POST /api/v1/data-assets
- **请求参数**:
  ```json
  {
    "assetCode": "string",
    "assetName": "string",
    "dsId": "string",
    "tableName": "string",
    "description": "string"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "assetCode": "string",
    "assetName": "string",
    "status": "string",
    "createdTime": "datetime"
  }
  ```

#### 1.3.2 查询数据资产列表
- **接口路径**: GET /api/v1/data-assets
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  keyword: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "assetCode": "string",
        "assetName": "string",
        "dsId": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 1.3.3 获取数据资产详情
- **接口路径**: GET /api/v1/data-assets/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "assetCode": "string",
    "assetName": "string",
    "dsId": "string",
    "tableName": "string",
    "schemaJson": "object",
    "description": "string",
    "status": "string",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

## 2. 工作流引擎平台API接口规范

### 2.1 流程定义管理API

#### 2.1.1 创建流程定义
- **接口路径**: POST /api/v1/workflow-definitions
- **请求参数**:
  ```json
  {
    "definitionKey": "string",
    "definitionName": "string",
    "definitionVersion": "string",
    "bpmnXml": "string",
    "description": "string"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "definitionKey": "string",
    "definitionName": "string",
    "definitionVersion": "string",
    "status": "string",
    "createdTime": "datetime"
  }
  ```

#### 2.1.2 查询流程定义列表
- **接口路径**: GET /api/v1/workflow-definitions
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  status: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "definitionKey": "string",
        "definitionName": "string",
        "definitionVersion": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 2.1.3 获取流程定义详情
- **接口路径**: GET /api/v1/workflow-definitions/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "definitionKey": "string",
    "definitionName": "string",
    "definitionVersion": "string",
    "bpmnXml": "string",
    "description": "string",
    "status": "string",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 2.1.4 部署流程定义
- **接口路径**: POST /api/v1/workflow-definitions/{id}/deploy
- **响应参数**:
  ```json
  {
    "id": "string",
    "definitionKey": "string",
    "status": "string",
    "deployedTime": "datetime"
  }
  ```

#### 2.1.5 删除流程定义
- **接口路径**: DELETE /api/v1/workflow-definitions/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

### 2.2 流程实例管理API

#### 2.2.1 启动流程实例
- **接口路径**: POST /api/v1/process-instances
- **请求参数**:
  ```json
  {
    "definitionId": "string",
    "businessKey": "string",
    "variables": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "definitionId": "string",
    "businessKey": "string",
    "status": "string",
    "startTime": "datetime"
  }
  ```

#### 2.2.2 查询流程实例列表
- **接口路径**: GET /api/v1/process-instances
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  status: string (optional)
  definitionId: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "definitionId": "string",
        "businessKey": "string",
        "status": "string",
        "startTime": "datetime"
      }
    ]
  }
  ```

#### 2.2.3 获取流程实例详情
- **接口路径**: GET /api/v1/process-instances/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "definitionId": "string",
    "businessKey": "string",
    "variables": "object",
    "status": "string",
    "startTime": "datetime",
    "endTime": "datetime"
  }
  ```

#### 2.2.4 挂起流程实例
- **接口路径**: POST /api/v1/process-instances/{id}/suspend
- **响应参数**:
  ```json
  {
    "id": "string",
    "status": "string",
    "suspendedTime": "datetime"
  }
  ```

#### 2.2.5 激活流程实例
- **接口路径**: POST /api/v1/process-instances/{id}/activate
- **响应参数**:
  ```json
  {
    "id": "string",
    "status": "string",
    "activatedTime": "datetime"
  }
  ```

#### 2.2.6 终止流程实例
- **接口路径**: POST /api/v1/process-instances/{id}/terminate
- **响应参数**:
  ```json
  {
    "id": "string",
    "status": "string",
    "terminatedTime": "datetime"
  }
  ```

### 2.3 任务实例管理API

#### 2.3.1 查询任务实例列表
- **接口路径**: GET /api/v1/task-instances
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  assignee: string (optional)
  status: string (optional)
  processInstanceId: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "taskDefinitionKey": "string",
        "taskName": "string",
        "assignee": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 2.3.2 获取任务实例详情
- **接口路径**: GET /api/v1/task-instances/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "taskDefinitionKey": "string",
    "taskName": "string",
    "assignee": "string",
    "processInstanceId": "string",
    "variables": "object",
    "status": "string",
    "createdTime": "datetime",
    "claimedTime": "datetime"
  }
  ```

#### 2.3.3 签收任务
- **接口路径**: POST /api/v1/task-instances/{id}/claim
- **请求参数**:
  ```json
  {
    "assignee": "string"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "assignee": "string",
    "status": "string",
    "claimedTime": "datetime"
  }
  ```

#### 2.3.4 完成任务
- **接口路径**: POST /api/v1/task-instances/{id}/complete
- **请求参数**:
  ```json
  {
    "variables": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "status": "string",
    "completedTime": "datetime"
  }
  ```

## 3. 零代码应用平台API接口规范

### 3.1 应用管理API

#### 3.1.1 创建应用
- **接口路径**: POST /api/v1/applications
- **请求参数**:
  ```json
  {
    "appCode": "string",
    "appName": "string",
    "description": "string",
    "configJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "appCode": "string",
    "appName": "string",
    "status": "string",
    "createdTime": "datetime"
  }
  ```

#### 3.1.2 查询应用列表
- **接口路径**: GET /api/v1/applications
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  status: string (optional)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "appCode": "string",
        "appName": "string",
        "status": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 3.1.3 获取应用详情
- **接口路径**: GET /api/v1/applications/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "appCode": "string",
    "appName": "string",
    "description": "string",
    "configJson": "object",
    "status": "string",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 3.1.4 更新应用
- **接口路径**: PUT /api/v1/applications/{id}
- **请求参数**:
  ```json
  {
    "appName": "string",
    "description": "string",
    "configJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "appCode": "string",
    "appName": "string",
    "status": "string",
    "updatedTime": "datetime"
  }
  ```

#### 3.1.5 删除应用
- **接口路径**: DELETE /api/v1/applications/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

#### 3.1.6 部署应用
- **接口路径**: POST /api/v1/applications/{id}/deploy
- **响应参数**:
  ```json
  {
    "id": "string",
    "status": "string",
    "deployedTime": "datetime"
  }
  ```

### 3.2 页面管理API

#### 3.2.1 创建页面
- **接口路径**: POST /api/v1/applications/{appId}/pages
- **请求参数**:
  ```json
  {
    "pageCode": "string",
    "pageTitle": "string",
    "layoutJson": "object",
    "componentsJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "pageCode": "string",
    "pageTitle": "string",
    "createdTime": "datetime"
  }
  ```

#### 3.2.2 查询页面列表
- **接口路径**: GET /api/v1/applications/{appId}/pages
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "pageCode": "string",
        "pageTitle": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 3.2.3 获取页面详情
- **接口路径**: GET /api/v1/applications/{appId}/pages/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "pageCode": "string",
    "pageTitle": "string",
    "layoutJson": "object",
    "componentsJson": "object",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 3.2.4 更新页面
- **接口路径**: PUT /api/v1/applications/{appId}/pages/{id}
- **请求参数**:
  ```json
  {
    "pageTitle": "string",
    "layoutJson": "object",
    "componentsJson": "object"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "pageCode": "string",
    "pageTitle": "string",
    "updatedTime": "datetime"
  }
  ```

#### 3.2.5 删除页面
- **接口路径**: DELETE /api/v1/applications/{appId}/pages/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

### 3.3 数据模型管理API

#### 3.3.1 创建数据模型
- **接口路径**: POST /api/v1/applications/{appId}/models
- **请求参数**:
  ```json
  {
    "modelCode": "string",
    "modelName": "string",
    "fieldsJson": "array",
    "relationsJson": "array"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "modelCode": "string",
    "modelName": "string",
    "createdTime": "datetime"
  }
  ```

#### 3.3.2 查询数据模型列表
- **接口路径**: GET /api/v1/applications/{appId}/models
- **请求参数**:
  ```
  page: integer (default: 1)
  size: integer (default: 10)
  ```
- **响应参数**:
  ```json
  {
    "total": "integer",
    "list": [
      {
        "id": "string",
        "modelCode": "string",
        "modelName": "string",
        "createdTime": "datetime"
      }
    ]
  }
  ```

#### 3.3.3 获取数据模型详情
- **接口路径**: GET /api/v1/applications/{appId}/models/{id}
- **响应参数**:
  ```json
  {
    "id": "string",
    "modelCode": "string",
    "modelName": "string",
    "fieldsJson": "array",
    "relationsJson": "array",
    "createdTime": "datetime",
    "updatedTime": "datetime"
  }
  ```

#### 3.3.4 更新数据模型
- **接口路径**: PUT /api/v1/applications/{appId}/models/{id}
- **请求参数**:
  ```json
  {
    "modelName": "string",
    "fieldsJson": "array",
    "relationsJson": "array"
  }
  ```
- **响应参数**:
  ```json
  {
    "id": "string",
    "modelCode": "string",
    "modelName": "string",
    "updatedTime": "datetime"
  }
  ```

#### 3.3.5 删除数据模型
- **接口路径**: DELETE /api/v1/applications/{appId}/models/{id}
- **响应参数**:
  ```json
  {
    "success": "boolean",
    "message": "string"
  }
  ```

## 4. 错误处理规范

### 4.1 错误响应格式
所有API在发生错误时，都会返回统一的错误响应格式：
```json
{
  "errorCode": "string",
  "errorMessage": "string",
  "errorDetails": "object"
}
```

### 4.2 常见错误码
| 错误码 | 错误信息 | 描述 |
|--------|----------|------|
| 400001 | 参数校验失败 | 请求参数不符合要求 |
| 400002 | 数据不存在 | 请求的资源不存在 |
| 400003 | 数据已存在 | 创建的资源已存在 |
| 500001 | 系统内部错误 | 服务器内部发生错误 |
| 500002 | 数据库操作失败 | 数据库操作过程中发生错误 |

## 5. 认证与授权

### 5.1 认证方式
所有API接口均采用JWT Token进行认证，客户端需要在请求头中添加：
```
Authorization: Bearer <token>
```

### 5.2 授权机制
平台采用RBAC（基于角色的访问控制）模型，不同角色拥有不同的API访问权限。
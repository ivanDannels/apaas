### **统一智能数据应用平台 (UDAP) - 软件详细设计文档**

| 文档版本 | 修订日期 | 修订人 | 修订说明 |
| :--- | :--- | :--- | :--- |
| V1.0 | 2025-09-11 | Gemini | 初始版本，整合所有上下文并进行详细设计 |
| V2.0 | 2025-08-04 | 开发助手 | 完善技术细节，补充开发指导内容 |
| V3.0 | 2025-09-11 | Lingma | 结合APaaS平台设计，完善系统功能设计、技术架构和数据模型等内容 |
| V4.0 | 2025-09-11 | Lingma | 增加详细技术设计内容，完善系统架构和模块设计 |
| V5.0 | 2025-09-11 | Lingma | 增加数据库脚本更新执行设计 |
| V6.0 | 2025-09-11 | Lingma | 采用Liquibase作为数据库版本管理工具，完善设计方案 |
| V7.0 | 2025-09-11 | Lingma | 增加数据库版本管理技术方案章节 |
| V8.0 | 2025-09-11 | Lingma | 细化Liquibase具体实施方案细节 |

-----

### **目录**

1.  **引言**
    1.1. 文档目的
    1.2. 项目背景与愿景
    1.3. 设计范围
    1.4. 术语表
2.  **系统总体架构**
    2.1. 架构原则
    2.2. 功能架构与微服务划分
    2.3. 技术架构栈
    2.4. 部署架构
3.  **通用设计规范 (Cross-Cutting Concerns)**
    3.1. API 设计规范 (RESTful & Versioning)
    3.2. 统一响应与异常处理
    3.3. 认证与授权机制 (JWT Flow)
    3.4. 多租户实现策略
    3.5. 日志与链路追踪规范
4.  **数据库设计**
    4.1. 核心实体关系图 (E-R Diagram)
    4.2. 核心物理表结构
    4.3. 数据库设计规范
    4.4. 数据库脚本更新执行设计
5.  **核心领域与微服务详细设计**
    5.1. **统一支撑平台**
    5.1.1. IAM上下文 (`udap-iam-service`)
    5.1.2. 项目协作上下文 (`udap-project-service`)
    5.1.3. 系统可观测性上下文 (`udap-monitoring-service`)
    5.2. **大数据平台**
    5.2.1. 数据集成上下文 (`udap-integration-service`)
    5.2.2. 数据治理上下文 (`udap-metadata-service`)
    5.3. **工作流引擎平台**
    5.3.1. 流程自动化上下文 (`udap-workflow-service`)
    5.4. **零代码应用平台**
    5.4.1. 低代码应用构建上下文 (`udac-zerocode-service`)
6.  **核心业务流程详细设计**
    6.1. 流程一：用户登录、鉴权与菜单加载
    6.2. 流程二：创建项目并添加成员
    6.3. 流程三：创建并发布一个数据同步工作流
    6.4. 流程四：零代码应用构建与部署
7.  **前端设计概要**
    7.1. 前端架构模式
    7.2. 核心目录结构
    7.3. 权限控制方案
    7.4. 状态管理
    7.5. 组件设计规范
8.  **非功能性需求设计**
    8.1. 性能与扩展性
    8.2. 高可用与容灾
    8.3. 安全性
    8.4. 监控与运维
9.  **开发规范与工具链**
    9.1. 代码规范
    9.2. 分支管理策略
    9.3. CI/CD流程
    9.4. 测试策略
10. **系统安全设计**
    10.1. 身份认证
    10.2. 权限控制
    10.3. 数据安全
    10.4. 操作审计
11. **高可用设计**
    11.1. 集群部署
    11.2. 限流熔断
    11.3. 缓存策略
    11.4. 故障恢复
12. **性能优化**
    12.1. 数据库优化
    12.2. 应用优化
    12.3. 前端优化
13. **多语言与国际化**
    13.1. 多语言支持
    13.2. 多时区支持
14. **集成与扩展**
    14.1. 插件机制
    14.2. API设计
    14.3. 事件驱动
15. **系统工程架构设计**
    15.1. 后端工程结构
    15.2. 前端工程结构
    15.3. 服务间依赖关系
    15.4. 部署与运维
16. **数据库版本管理技术方案**
    16.1. 技术选型与架构设计
    16.2. 核心组件与工作原理
    16.3. 集成方案与配置
    16.4. 变更管理与版本控制
    16.5. 回滚与恢复机制
    16.6. 监控与审计
    16.7. 最佳实践与注意事项

-----

### **1. 引言**

#### **1.1. 文档目的**

本⽂档旨在为"统一智能数据应用平台 (UDAP)"项⽬提供全⾯、详细的设计⽅案，作为开发团队进⾏编码、测试和部署的主要依据。本文档详细描述了系统架构、领域模型、接⼝设计、数据结构和核⼼业务流程，确保开发⼯作的⼀致性、规范性和⾼效率。

#### **1.2. 项目背景与愿景**

统一智能数据应用平台 (UDAP) 是一个面向企业的云原生数据应用平台，通过零代码/低代码方式，帮助企业快速构建数据驱动的业务应用。平台提供统一的数据集成、治理、分析和应用构建能力，支持多租户、高并发、高可用的企业级应用场景。

#### **1.3. 设计范围**

本设计覆盖了UDAP平台的完整技术栈：
- 后端：基于Spring Cloud的微服务架构
- 前端：基于Vue 3 + TypeScript的SPA应用
- 数据库：PostgreSQL主库 + Redis缓存
- 中间件：Kafka消息队列、Nacos注册配置中心
- 基础设施：Docker容器化 + Kubernetes编排

#### **1.4. 术语表**

| 术语 | 英文 | 解释 |
| :--- | :--- | :--- |
| 租户 | Tenant | 系统的独立使用实例，拥有隔离的用户、项目和数据 |
| 项目 | Project | 租户内的一个逻辑工作空间，用于隔离和管理资源 |
| 应用 | Application | 基于零代码平台构建的业务应用 |
| 工作流 | Workflow | 由用户定义的自动化业务流程 |
| RBAC | Role-Based Access Control | 基于角色的访问控制 |
| DDD | Domain-Driven Design | 领域驱动设计 |
| 限界上下文 | Bounded Context | DDD术语，一个清晰的业务边界 |
| 聚合根 | Aggregate Root | 聚合的主要入口实体 |
| DTO | Data Transfer Object | 数据传输对象 |
| API | Application Programming Interface | 应用程序接口 |

-----

### **2. 系统总体架构**

#### **2.1. 架构原则**

1. **云原生优先**：所有服务容器化，支持Kubernetes编排
2. **领域驱动设计**：基于DDD划分微服务边界
3. **事件驱动架构**：服务间通过领域事件解耦
4. **前后端分离**：独立开发、部署、扩展
5. **安全优先**：零信任安全模型，全链路加密
6. **可观测性**：全面的监控、日志、链路追踪

#### **2.2. 功能架构与微服务划分**

| 限界上下文 | 微服务名称 | 主要职责 | 技术栈 |
| :--- | :--- | :--- | :--- |
| **统一支撑平台** |
| 身份与访问管理 | `udap-iam-service` | 租户、用户、角色、权限管理，OAuth2/JWT认证 | Spring Boot + Spring Security |
| 项目协作 | `udap-project-service` | 项目创建、成员管理、资源配额 | Spring Boot + JPA |
| 系统可观测性 | `udap-monitoring-service` | 告警规则管理、告警事件处理 | Spring Boot + Micrometer |
| **大数据平台** |
| 数据集成 | `udap-integration-service` | 数据源管理，数据同步任务执行 | Spring Boot + Quartz |
| 数据治理 | `udap-metadata-service` | 元数据采集、数据血缘、数据资产目录 | Spring Boot + Neo4j |
| **工作流引擎平台** |
| 流程自动化 | `udap-workflow-service` | 工作流定义与实例管理，任务编排 | Spring Boot + Activiti |
| **零代码应用平台** |
| 低代码应用构建 | `udac-zerocode-service` | 零代码数据模型、页面、逻辑元数据管理 | Spring Boot + MongoDB |
| **网关层** |
| API网关 | `udap-gateway` | 统一入口，路由、鉴权、限流 | Spring Cloud Gateway |

#### **2.3. 技术架构栈**

**后端技术栈**
- **框架**：Spring Boot 4.0.0-M1, Spring Cloud 2025.0.0, Spring Cloud Alibaba 2023.0.3.3
- **安全**：Spring Security 6.x, JWT (jjwt), OAuth2
- **数据访问**：Spring Data JPA, MyBatis-Plus, Hibernate 6.x, R2DBC 42.7.2
- **数据库**：PostgreSQL 16.1 (主库), Redis 7.x (缓存)
- **消息队列**：Apache Kafka 3.x, RabbitMQ 5.20.0
- **注册中心**：Nacos 2.x
- **监控**：Micrometer + Prometheus + Grafana
- **链路追踪**：OpenTelemetry + Jaeger, SkyWalking 9.1.0
- **分布式事务**：Seata 2.0.0
- **任务调度**：XXL-JOB 3.1.1
- **文档**：SpringDoc OpenAPI 2.8.9
- **对象存储**：MinIO 8.5.17
- **工具库**：Hutool 5.8.39, Lombok

**前端技术栈**
- **框架**：Vue 3.5.18 + TypeScript 5.x
- **构建工具**：Vite 5.x
- **UI组件库**：Element Plus 2.x
- **状态管理**：Pinia 2.x
- **路由**：Vue Router 4.x
- **HTTP客户端**：Axios 1.x
- **图表**：ECharts 5.x
- **多语言**：Vue I18n 11.1.11

**基础设施**
- **容器化**：Docker 25.0.0
- **编排**：Kubernetes 1.28+, Docker Compose 2.24.5
- **CI/CD**：Jenkins 2.450.0
- **代码质量**：SonarQube
- **对象存储**：MinIO 8.5.17

#### **2.4. 部署架构**

```yaml
# 部署架构图（概念）
Internet
    |
    v
[Load Balancer (Nginx)]
    |
    v
[API Gateway (udap-gateway)]
    |
    +---> [Microservices Cluster]
    |       ├── udap-iam-service
    |       ├── udap-project-service
    |       ├── udap-integration-service
    |       ├── udap-workflow-service
    |       └── ...
    |
    +---> [Data Layer]
    |       ├── PostgreSQL (HA)
    |       ├── Redis Cluster
    |       └── MongoDB ReplicaSet
    |
    +---> [Message Queue]
    |       └── Kafka Cluster
    |       └── RabbitMQ Cluster
    |
    +---> [Monitoring]
        ├── Prometheus
        ├── Grafana
        └── Jaeger
```

-----

### **3. 通用设计规范 (Cross-Cutting Concerns)**

#### **3.1. API 设计规范 (RESTful & Versioning)**

**基础规范**
- **协议**：HTTPS
- **基础路径**：`https://domain.com/api/v1`
- **版本管理**：URL路径版本控制
- **命名规范**：
  - 使用小写字母和连字符
  - 资源使用复数名词：`/users`, `/projects`
  - 动词使用HTTP方法：GET, POST, PUT, DELETE, PATCH

**HTTP状态码规范**
| 状态码 | 场景 | 响应格式 |
| --- | --- | --- |
| 200 | 成功 | `{code: 0, message: "Success", data: {...}}` |
| 201 | 创建成功 | `{code: 0, message: "Created", data: {...}}` |
| 400 | 参数错误 | `{code: 400, message: "Invalid parameters", data: null}` |
| 401 | 未认证 | `{code: 401, message: "Unauthorized", data: null}` |
| 403 | 无权限 | `{code: 403, message: "Forbidden", data: null}` |
| 404 | 资源不存在 | `{code: 404, message: "Not found", data: null}` |
| 500 | 服务器错误 | `{code: 500, message: "Internal server error", data: null}` |

#### **3.2. 统一响应与异常处理**

**统一响应结构**
```json
{
  "code": 0,
  "message": "Success",
  "data": {},
  "timestamp": "2024-08-04T14:30:00Z",
  "requestId": "uuid"
}
```

**全局异常处理**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getStatus())
            .body(ApiResponse.error(e.getCode(), e.getMessage()));
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(400, e.getMessage()));
    }
}
```

#### **3.3. 认证与授权机制 (JWT Flow)**

**JWT Token结构**
```json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "userId": "123456",
    "username": "admin",
    "tenantId": "tenant_001",
    "roles": ["admin", "user"],
    "exp": 1691234567,
    "iat": 1691230967
  }
}
```

**认证流程**
1. 用户登录：`POST /api/v1/iam/auth/login`
2. 返回JWT：包含access_token和refresh_token
3. 后续请求：在Header中添加 `Authorization: Bearer <token>`
4. 网关验证：统一在API网关层验证JWT
5. 权限传递：将用户信息透传给下游服务

#### **3.4. 多租户实现策略**

**租户隔离方案**
- **数据隔离**：PostgreSQL Schema隔离
- **应用隔离**：通过租户ID字段实现
- **配置隔离**：Nacos配置中心按租户分组

**技术实现**
```java
// 租户上下文
public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();
    
    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }
    
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
}

// JPA多租户配置
@Entity
@Table(name = "users")
@Multitenancy
@TenantDiscriminatorColumn(name = "tenant_id")
public class User {
    // ...
}
```

#### **3.5. 日志与链路追踪规范**

**日志规范**
- **格式**：JSON结构化日志
- **级别**：ERROR, WARN, INFO, DEBUG, TRACE
- **内容**：时间戳、线程、类名、traceId、业务标识

**链路追踪配置**
```yaml
# application.yml
management:
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://jaeger:14268/api/v2/spans
```

**日志示例**
```json
{
  "timestamp": "2024-08-04T14:30:00.123Z",
  "level": "INFO",
  "thread": "http-nio-8080-exec-1",
  "logger": "com.udap.iam.rest.AuthController",
  "message": "User login successful",
  "traceId": "a1b2c3d4e5f6",
  "spanId": "g7h8i9j0",
  "tenantId": "tenant_001",
  "userId": "123456"
}
```

-----

### **4. 数据库设计**

#### **4.1. 核心实体关系图 (E-R Diagram)**

```mermaid
erDiagram
    Tenant ||--o{ User : has
    Tenant ||--o{ Project : has
    User ||--o{ ProjectMember : participates
    Project ||--o{ ProjectMember : has
    User ||--o{ UserRole : has
    Role ||--o{ UserRole : assigned_to
    Role ||--o{ RolePermission : has
    Permission ||--o{ RolePermission : assigned_to
    
    Tenant {
        string id PK
        string name
        string code
        string status
        datetime created_at
        datetime updated_at
    }
    
    User {
        string id PK
        string tenant_id FK
        string username
        string email
        string password_hash
        string status
        datetime created_at
        datetime updated_at
    }
    
    Project {
        string id PK
        string tenant_id FK
        string name
        string description
        string status
        datetime created_at
        datetime updated_at
    }
    
    ProjectMember {
        string id PK
        string project_id FK
        string user_id FK
        string role
        datetime created_at
    }
    
    Role {
        string id PK
        string tenant_id FK
        string name
        string code
        string description
        datetime created_at
    }
    
    Permission {
        string id PK
        string code
        string name
        string resource
        string action
        datetime created_at
    }
```

#### **4.2. 核心物理表结构**

**租户表 (tenants)**
```sql
CREATE TABLE tenants (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    max_users INT DEFAULT 100,
    max_projects INT DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36)
);
```

**用户表 (users)**
```sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    tenant_id VARCHAR(36) NOT NULL REFERENCES tenants(id),
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    avatar_url VARCHAR(500),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    last_login_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tenant_id, username),
    UNIQUE(tenant_id, email)
);
```

**项目表 (projects)**
```sql
CREATE TABLE projects (
    id VARCHAR(36) PRIMARY KEY,
    tenant_id VARCHAR(36) NOT NULL REFERENCES tenants(id),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    owner_id VARCHAR(36) NOT NULL REFERENCES users(id),
    settings JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tenant_id, name)
);
```

**角色权限表 (roles, permissions, role_permissions)**
```sql
CREATE TABLE roles (
    id VARCHAR(36) PRIMARY KEY,
    tenant_id VARCHAR(36) NOT NULL REFERENCES tenants(id),
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    type VARCHAR(20) DEFAULT 'CUSTOM',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tenant_id, code)
);

CREATE TABLE permissions (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    resource VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE role_permissions (
    role_id VARCHAR(36) REFERENCES roles(id),
    permission_id VARCHAR(36) REFERENCES permissions(id),
    PRIMARY KEY (role_id, permission_id)
);
```

**数据字典表 (data_dictionary, data_dictionary_item)**
```sql
CREATE TABLE data_dictionary (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL,
    type INT NOT NULL DEFAULT 0,
    status INT NOT NULL DEFAULT 0,
    description VARCHAR(255),
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(50),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INT NOT NULL DEFAULT 0,
    UNIQUE(code, tenant_id)
);

CREATE TABLE data_dictionary_item (
    id BIGSERIAL PRIMARY KEY,
    dictionary_id BIGINT NOT NULL,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    value VARCHAR(255),
    sort INT NOT NULL DEFAULT 0,
    status INT NOT NULL DEFAULT 0,
    description VARCHAR(255),
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(50),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted INT NOT NULL DEFAULT 0
);
```

**流程定义表 (flow_definition)**
```sql
CREATE TABLE flow_definition (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(64) NOT NULL,
    category VARCHAR(64),
    version INT NOT NULL DEFAULT 1,
    description TEXT,
    flow_json TEXT,
    form_id BIGINT,
    status TINYINT NOT NULL DEFAULT 0,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_version (code, version, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**流程实例表 (flow_instance)**
```sql
CREATE TABLE flow_instance (
    id BIGINT NOT NULL,
    definition_id BIGINT NOT NULL,
    definition_name VARCHAR(255) NOT NULL,
    definition_code VARCHAR(64) NOT NULL,
    business_key VARCHAR(64),
    business_data TEXT,
    current_node_id VARCHAR(64),
    current_node_name VARCHAR(255),
    status TINYINT NOT NULL,
    start_user_id BIGINT NOT NULL,
    start_user_name VARCHAR(64) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX idx_definition_id (definition_id),
    INDEX idx_tenant_status (tenant_id, status),
    INDEX idx_start_user (start_user_id)
);
```

**流程任务表 (flow_task)**
```sql
CREATE TABLE flow_task (
    id BIGINT NOT NULL,
    instance_id BIGINT NOT NULL,
    instance_name VARCHAR(255) NOT NULL,
    definition_id BIGINT NOT NULL,
    node_id VARCHAR(64) NOT NULL,
    node_name VARCHAR(255) NOT NULL,
    node_type TINYINT NOT NULL,
    status TINYINT NOT NULL,
    priority TINYINT NOT NULL DEFAULT 0,
    assignee_id BIGINT,
    assignee_name VARCHAR(64),
    candidate_ids TEXT,
    candidate_names TEXT,
    create_time DATETIME NOT NULL,
    start_time DATETIME,
    handle_time DATETIME,
    complete_time DATETIME,
    handle_user_id BIGINT,
    handle_user_name VARCHAR(64),
    comment TEXT,
    variables TEXT,
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX idx_assignee_id (assignee_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**表单定义表 (form_definition)**
```sql
CREATE TABLE form_definition (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(64) NOT NULL,
    type TINYINT NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 0,
    config_json TEXT,
    items_json TEXT NOT NULL,
    data_source_id BIGINT,
    flow_id BIGINT,
    version INT NOT NULL DEFAULT 1,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    tenant_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_version (code, version, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**系统资源配置表 (sys_resource)**
```sql
CREATE TABLE sys_resource (
    id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    resource_name VARCHAR(128) NOT NULL,
    resource_code VARCHAR(128) NOT NULL,
    resource_type TINYINT NOT NULL,
    parent_id BIGINT,
    resource_path VARCHAR(255),
    icon VARCHAR(64),
    sort_order INT,
    status TINYINT NOT NULL DEFAULT 1,
    permission_expression VARCHAR(255),
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_resource_code (resource_code, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**角色资源关联表 (sys_role_resource)**
```sql
CREATE TABLE sys_role_resource (
    role_id BIGINT NOT NULL,
    resource_id BIGINT NOT NULL,
    permissions VARCHAR(128),
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    PRIMARY KEY (role_id, resource_id)
);
```

**数据范围定义表 (sys_data_scope)**
```sql
CREATE TABLE sys_data_scope (
    id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    scope_code VARCHAR(64) NOT NULL,
    scope_name VARCHAR(128) NOT NULL,
    scope_type VARCHAR(32) NOT NULL,
    scope_value TEXT,
    description VARCHAR(255),
    status TINYINT NOT NULL DEFAULT 1,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_scope_code (scope_code, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**角色数据范围关联表 (sys_role_data_scope)**
```sql
CREATE TABLE sys_role_data_scope (
    role_id BIGINT NOT NULL,
    scope_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    PRIMARY KEY (role_id, scope_id)
);
```

**通知渠道表 (sys_notification_channel)**
```sql
CREATE TABLE sys_notification_channel (
    id BIGINT NOT NULL,
    channel_code VARCHAR(32) NOT NULL,
    channel_name VARCHAR(64) NOT NULL,
    description VARCHAR(255),
    config_json JSON,
    status TINYINT NOT NULL DEFAULT 1,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_channel_code (channel_code)
);
```

**通知模板表 (sys_notification_template)**
```sql
CREATE TABLE sys_notification_template (
    id BIGINT NOT NULL,
    template_code VARCHAR(64) NOT NULL,
    template_name VARCHAR(128) NOT NULL,
    channel_id BIGINT NOT NULL,
    title VARCHAR(255),
    content TEXT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    trigger_event VARCHAR(64),
    status TINYINT NOT NULL DEFAULT 1,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_template_code (template_code)
);
```

**用户通知订阅表 (sys_user_notification_subscription)**
```sql
CREATE TABLE sys_user_notification_subscription (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    channel_id BIGINT NOT NULL,
    template_id BIGINT NOT NULL,
    is_subscribed TINYINT NOT NULL DEFAULT 1,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);
```

**组织机构表 (sys_organization)**
```sql
CREATE TABLE sys_organization (
    id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    org_code VARCHAR(64) NOT NULL,
    org_name VARCHAR(128) NOT NULL,
    parent_id BIGINT,
    org_type VARCHAR(32),
    leader_user_id BIGINT,
    description VARCHAR(255),
    status TINYINT NOT NULL DEFAULT 1,
    sort_order INT,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_org_code (org_code, tenant_id),
    INDEX idx_tenant_status (tenant_id, status)
);
```

**岗位信息表 (sys_position)**
```sql
CREATE TABLE sys_position (
    id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    position_code VARCHAR(64) NOT NULL,
    position_name VARCHAR(128) NOT NULL,
    description VARCHAR(255),
    status TINYINT NOT NULL DEFAULT 1,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    update_by VARCHAR(64),
    update_time DATETIME NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_position_code (position_code, tenant_id)
);
```

**用户组织机构关联表 (sys_user_organization)**
```sql
CREATE TABLE sys_user_organization (
    user_id BIGINT NOT NULL,
    org_id BIGINT NOT NULL,
    is_main_org TINYINT NOT NULL DEFAULT 0,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    PRIMARY KEY (user_id, org_id)
);
```

**用户岗位关联表 (sys_user_position)**
```sql
CREATE TABLE sys_user_position (
    user_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    create_by VARCHAR(64),
    create_time DATETIME NOT NULL,
    PRIMARY KEY (user_id, position_id)
);
```

#### **4.3. 数据库设计规范**

**命名规范**
- 表名：使用小写下划线命名，复数形式：`users`, `projects`
- 字段名：使用小写下划线命名：`created_at`, `user_id`
- 索引名：`idx_表名_字段名`

**字段规范**
- 主键：UUID字符串，字段名`id`
- 时间戳：统一使用`created_at`, `updated_at`
- 软删除：使用`deleted_at`字段，NULL表示未删除
- JSON字段：使用PostgreSQL的JSONB类型存储复杂数据

**索引规范**
- 主键：自动创建
- 外键：自动创建索引
- 查询字段：高频查询字段创建复合索引
- 唯一约束：业务唯一字段创建唯一索引

-----

### **5. 核心领域与微服务详细设计**

#### **5.1. 统一支撑平台**

##### **5.1.1. IAM上下文 (`udap-iam-service`)**

**领域模型**
```java
// 聚合根：Tenant
@Entity
@Table(name = "tenants")
public class Tenant extends AggregateRoot {
    @Id
    private String id;
    
    @Embedded
    private TenantInfo info;
    
    @Embedded
    private TenantLimits limits;
    
    @Enumerated(EnumType.STRING)
    private TenantStatus status;
    
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
    
    // 领域行为
    public void activate() {
        if (this.status == TenantStatus.ACTIVE) {
            throw new BusinessException("Tenant already active");
        }
        this.status = TenantStatus.ACTIVE;
        registerEvent(new TenantActivatedEvent(this.id));
    }
}

// 值对象：用户名
@Embeddable
public class Username {
    private final String value;
    
    public Username(String value) {
        if (value == null || !value.matches("^[a-zA-Z0-9_]{3,20}$")) {
            throw new ValidationException("Invalid username format");
        }
        this.value = value;
    }
}

// 聚合根：User
@Entity
@Table(name = "users")
public class User extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    @Embedded
    private Username username;
    
    private String email;
    
    private String passwordHash;
    
    @Embedded
    private UserProfile profile;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> roles = new HashSet<>();
    
    // 领域行为
    public void changePassword(String oldPassword, String newPassword) {
        if (!passwordMatches(oldPassword)) {
            throw new BusinessException("Old password is incorrect");
        }
        this.passwordHash = hashPassword(newPassword);
        registerEvent(new UserPasswordChangedEvent(this.id));
    }
    
    public void assignRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        this.roles.add(userRole);
        registerEvent(new UserRoleAssignedEvent(this.id, role.getId()));
    }
    
    public void deactivate() {
        this.status = UserStatus.INACTIVE;
        registerEvent(new UserDeactivatedEvent(this.id));
    }
}

// 聚合根：Role
@Entity
@Table(name = "roles")
public class Role extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String code;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private RoleType type;
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RolePermission> permissions = new HashSet<>();
    
    // 领域行为
    public void assignPermission(Permission permission) {
        RolePermission rolePermission = new RolePermission(this, permission);
        this.permissions.add(rolePermission);
        registerEvent(new RolePermissionAssignedEvent(this.id, permission.getId()));
    }
    
    public void revokePermission(Permission permission) {
        this.permissions.removeIf(rp -> rp.getPermissionId().equals(permission.getId()));
        registerEvent(new RolePermissionRevokedEvent(this.id, permission.getId()));
    }
}
```

**核心API接口**

**认证相关**
```java
@RestController
@RequestMapping("/api/v1/iam/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新Token")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
```

**用户管理**
```java
@RestController
@RequestMapping("/api/v1/iam/users")
@Tag(name = "用户管理", description = "用户管理接口")
public class UserController {

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser() {
        UserProfileResponse response = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/me")
    @Operation(summary = "更新当前用户信息")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request) {
        UserProfileResponse response = userService.updateCurrentUser(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me/permissions")
    @Operation(summary = "获取当前用户权限")
    public ResponseEntity<ApiResponse<UserPermissionResponse>> getCurrentUserPermissions() {
        UserPermissionResponse response = permissionService.getCurrentUserPermissions();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/{userId}/roles")
    @Operation(summary = "为用户分配角色")
    public ResponseEntity<ApiResponse<Void>> assignRoleToUser(
            @PathVariable String userId,
            @Valid @RequestBody AssignRoleRequest request) {
        userService.assignRoleToUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
```

**数据访问层**
```java
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
    Optional<User> findByTenantIdAndUsername(String tenantId, String username);
    
    Optional<User> findByTenantIdAndEmail(String tenantId, String email);
    
    boolean existsByTenantIdAndUsername(String tenantId, String username);
    
    Page<User> findByTenantId(String tenantId, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.tenantId = :tenantId AND u.status = :status")
    List<User> findActiveUsersByTenantId(@Param("tenantId") String tenantId, @Param("status") UserStatus status);
}

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByTenantIdAndCode(String tenantId, String code);
    
    List<Role> findByTenantId(String tenantId);
}
```

##### **5.1.2. 项目协作上下文 (`udap-project-service`)**

**领域模型**
```java
// 聚合根：Project
@Entity
@Table(name = "projects")
public class Project extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    @Embedded
    private ProjectInfo info;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectMember> members = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    
    // 领域行为
    public void addMember(User user, ProjectRole role) {
        if (members.size() >= getMaxMembers()) {
            throw new BusinessException("Project member limit reached");
        }
        
        ProjectMember member = new ProjectMember(this, user, role);
        members.add(member);
        registerEvent(new ProjectMemberAddedEvent(this.id, user.getId(), role));
    }
    
    public void removeMember(User user) {
        members.removeIf(member -> member.getUserId().equals(user.getId()));
        registerEvent(new ProjectMemberRemovedEvent(this.id, user.getId()));
    }
    
    public void archive() {
        this.status = ProjectStatus.ARCHIVED;
        registerEvent(new ProjectArchivedEvent(this.id));
    }
}
```

**核心API接口**
```java
@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "项目管理", description = "项目管理接口")
public class ProjectController {
    
    @PostMapping
    @Operation(summary = "创建项目")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取项目详情")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProject(
            @PathVariable String id) {
        ProjectResponse response = projectService.getProject(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新项目信息")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable String id,
            @Valid @RequestBody UpdateProjectRequest request) {
        ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除项目")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
            @PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/members")
    @Operation(summary = "添加项目成员")
    public ResponseEntity<ApiResponse<Void>> addMember(
            @PathVariable String id,
            @Valid @RequestBody AddMemberRequest request) {
        projectService.addMember(id, request);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @DeleteMapping("/{id}/members/{userId}")
    @Operation(summary = "移除项目成员")
    public ResponseEntity<ApiResponse<Void>> removeMember(
            @PathVariable String id,
            @PathVariable String userId) {
        projectService.removeMember(id, userId);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/archive")
    @Operation(summary = "归档项目")
    public ResponseEntity<ApiResponse<Void>> archiveProject(@PathVariable String id) {
        projectService.archiveProject(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
```

##### **5.1.3. 系统可观测性上下文 (`udap-monitoring-service`)**

**领域模型**
```java
// 聚合根：AlertRule
@Entity
@Table(name = "alert_rules")
public class AlertRule extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String description;
    
    @Embedded
    private AlertCondition condition;
    
    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;
    
    @Enumerated(EnumType.STRING)
    private AlertStatus status;
    
    // 领域行为
    public void enable() {
        this.status = AlertStatus.ENABLED;
        registerEvent(new AlertRuleEnabledEvent(this.id));
    }
    
    public void disable() {
        this.status = AlertStatus.DISABLED;
        registerEvent(new AlertRuleDisabledEvent(this.id));
    }
    
    public boolean evaluate(MetricData data) {
        return condition.evaluate(data);
    }
}

// 值对象：AlertCondition
@Embeddable
public class AlertCondition {
    private String metricName;
    private Double threshold;
    private String operator; // >, <, >=, <=, ==, !=
    private Integer durationMinutes;
    
    public boolean evaluate(MetricData data) {
        if (!data.getMetricName().equals(metricName)) {
            return false;
        }
        
        Double value = data.getValue();
        switch (operator) {
            case ">": return value > threshold;
            case "<": return value < threshold;
            case ">=": return value >= threshold;
            case "<=": return value <= threshold;
            case "==": return value.equals(threshold);
            case "!=": return !value.equals(threshold);
            default: return false;
        }
    }
}
```

#### **5.2. 大数据平台**

##### **5.2.1. 数据集成上下文 (`udap-integration-service`)**

**领域模型**
```java
// 聚合根：DataSource
@Entity
@Table(name = "data_sources")
public class DataSource extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String type;
    
    @Embedded
    private ConnectionConfig config;
    
    @Enumerated(EnumType.STRING)
    private DataSourceStatus status;
    
    // 领域行为
    public void testConnection() {
        // 测试数据源连接逻辑
        registerEvent(new DataSourceConnectionTestedEvent(this.id));
    }
    
    public Connection getConnection() {
        // 根据配置创建连接
        return connectionFactory.createConnection(this.type, this.config);
    }
    
    public void disable() {
        this.status = DataSourceStatus.DISABLED;
        registerEvent(new DataSourceDisabledEvent(this.id));
    }
}
```

**核心API接口**
```java
@RestController
@RequestMapping("/api/v1/integration/datasources")
@Tag(name = "数据源管理", description = "数据源管理接口")
public class DataSourceController {
    
    @PostMapping
    @Operation(summary = "创建数据源")
    public ResponseEntity<ApiResponse<DataSourceResponse>> createDataSource(
            @Valid @RequestBody CreateDataSourceRequest request) {
        DataSourceResponse response = dataSourceService.createDataSource(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取数据源详情")
    public ResponseEntity<ApiResponse<DataSourceResponse>> getDataSource(
            @PathVariable String id) {
        DataSourceResponse response = dataSourceService.getDataSource(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新数据源")
    public ResponseEntity<ApiResponse<DataSourceResponse>> updateDataSource(
            @PathVariable String id,
            @Valid @RequestBody UpdateDataSourceRequest request) {
        DataSourceResponse response = dataSourceService.updateDataSource(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据源")
    public ResponseEntity<ApiResponse<Void>> deleteDataSource(
            @PathVariable String id) {
        dataSourceService.deleteDataSource(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/test")
    @Operation(summary = "测试数据源连接")
    public ResponseEntity<ApiResponse<Void>> testConnection(
            @PathVariable String id) {
        dataSourceService.testConnection(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用数据源")
    public ResponseEntity<ApiResponse<Void>> disableDataSource(
            @PathVariable String id) {
        dataSourceService.disableDataSource(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
```

##### **5.2.2. 数据治理上下文 (`udap-metadata-service`)**

**领域模型**
```java
// 聚合根：DataAsset
@Entity
@Table(name = "data_assets")
public class DataAsset extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String description;
    
    @Embedded
    private AssetLocation location;
    
    @Enumerated(EnumType.STRING)
    private AssetType type;
    
    @Enumerated(EnumType.STRING)
    private AssetStatus status;
    
    // 领域行为
    public void updateMetadata(Map<String, Object> metadata) {
        // 更新元数据逻辑
        registerEvent(new DataAssetMetadataUpdatedEvent(this.id, metadata));
    }
    
    public void deprecate() {
        this.status = AssetStatus.DEPRECATED;
        registerEvent(new DataAssetDeprecatedEvent(this.id));
    }
    
    public void delete() {
        this.status = AssetStatus.DELETED;
        registerEvent(new DataAssetDeletedEvent(this.id));
    }
}
```

#### **5.3. 工作流引擎平台**

##### **5.3.1. 流程自动化上下文 (`udap-workflow-service`)**

**领域模型**
```java
// 聚合根：WorkflowDefinition
@Entity
@Table(name = "workflow_definitions")
public class WorkflowDefinition extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String description;
    
    @Lob
    private String definition;
    
    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;
    
    private Integer version;
    
    // 领域行为
    public void publish() {
        this.status = WorkflowStatus.PUBLISHED;
        registerEvent(new WorkflowDefinitionPublishedEvent(this.id));
    }
    
    public void suspend() {
        this.status = WorkflowStatus.SUSPENDED;
        registerEvent(new WorkflowDefinitionSuspendedEvent(this.id));
    }
    
    public WorkflowDefinition createNewVersion() {
        WorkflowDefinition newVersion = new WorkflowDefinition();
        newVersion.tenantId = this.tenantId;
        newVersion.name = this.name;
        newVersion.description = this.description;
        newVersion.definition = this.definition;
        newVersion.version = this.version + 1;
        newVersion.status = WorkflowStatus.DRAFT;
        return newVersion;
    }
    
    public void delete() {
        this.status = WorkflowStatus.DELETED;
        registerEvent(new WorkflowDefinitionDeletedEvent(this.id));
    }
}
```

**核心API接口**
```java
@RestController
@RequestMapping("/api/v1/workflow/definitions")
@Tag(name = "流程定义", description = "流程定义管理接口")
public class WorkflowDefinitionController {
    
    @PostMapping
    @Operation(summary = "创建流程定义")
    public ResponseEntity<ApiResponse<WorkflowDefinitionResponse>> createDefinition(
            @Valid @RequestBody CreateWorkflowDefinitionRequest request) {
        WorkflowDefinitionResponse response = workflowService.createDefinition(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取流程定义详情")
    public ResponseEntity<ApiResponse<WorkflowDefinitionResponse>> getDefinition(
            @PathVariable String id) {
        WorkflowDefinitionResponse response = workflowService.getDefinition(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新流程定义")
    public ResponseEntity<ApiResponse<WorkflowDefinitionResponse>> updateDefinition(
            @PathVariable String id,
            @Valid @RequestBody UpdateWorkflowDefinitionRequest request) {
        WorkflowDefinitionResponse response = workflowService.updateDefinition(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除流程定义")
    public ResponseEntity<ApiResponse<Void>> deleteDefinition(
            @PathVariable String id) {
        workflowService.deleteDefinition(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/publish")
    @Operation(summary = "发布流程定义")
    public ResponseEntity<ApiResponse<Void>> publishDefinition(
            @PathVariable String id) {
        workflowService.publishDefinition(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/version")
    @Operation(summary = "创建新版本")
    public ResponseEntity<ApiResponse<WorkflowDefinitionResponse>> createNewVersion(
            @PathVariable String id) {
        WorkflowDefinitionResponse response = workflowService.createNewVersion(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/{id}/suspend")
    @Operation(summary = "挂起流程定义")
    public ResponseEntity<ApiResponse<Void>> suspendDefinition(
            @PathVariable String id) {
        workflowService.suspendDefinition(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
```

#### **5.4. 零代码应用平台**

##### **5.4.1. 低代码应用构建上下文 (`udac-zerocode-service`)**

**领域模型**
```java
// 聚合根：Application
@Entity
@Table(name = "applications")
public class Application extends AggregateRoot {
    @Id
    private String id;
    
    private String tenantId;
    
    private String name;
    
    private String description;
    
    @Embedded
    private ApplicationConfig config;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    // 领域行为
    public void deploy() {
        this.status = ApplicationStatus.DEPLOYED;
        registerEvent(new ApplicationDeployedEvent(this.id));
    }
    
    public void undeploy() {
        this.status = ApplicationStatus.UNDEPLOYED;
        registerEvent(new ApplicationUndeployedEvent(this.id));
    }
    
    public void updateConfig(ApplicationConfig newConfig) {
        this.config = newConfig;
        registerEvent(new ApplicationConfigUpdatedEvent(this.id, newConfig));
    }
    
    public void delete() {
        this.status = ApplicationStatus.DELETED;
        registerEvent(new ApplicationDeletedEvent(this.id));
    }
}
```

**核心API接口**
```java
@RestController
@RequestMapping("/api/v1/zerocode/applications")
@Tag(name = "零代码应用", description = "零代码应用管理接口")
public class ApplicationController {
    
    @PostMapping
    @Operation(summary = "创建应用")
    public ResponseEntity<ApiResponse<ApplicationResponse>> createApplication(
            @Valid @RequestBody CreateApplicationRequest request) {
        ApplicationResponse response = applicationService.createApplication(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取应用详情")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getApplication(
            @PathVariable String id) {
        ApplicationResponse response = applicationService.getApplication(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新应用")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateApplication(
            @PathVariable String id,
            @Valid @RequestBody UpdateApplicationRequest request) {
        ApplicationResponse response = applicationService.updateApplication(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除应用")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(
            @PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/deploy")
    @Operation(summary = "部署应用")
    public ResponseEntity<ApiResponse<Void>> deployApplication(
            @PathVariable String id) {
        applicationService.deployApplication(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/{id}/undeploy")
    @Operation(summary = "卸载应用")
    public ResponseEntity<ApiResponse<Void>> undeployApplication(
            @PathVariable String id) {
        applicationService.undeployApplication(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
```

### **6. 核心业务流程详细设计**

#### **6.1. 流程一：用户登录、鉴权与菜单加载**

**流程概述**
用户通过用户名密码登录系统，系统验证身份后返回JWT Token，前端使用Token获取用户权限信息和菜单配置。

**时序图**
```mermaid
sequenceDiagram
    participant U as 用户
    participant F as 前端
    participant G as 网关
    participant A as 认证服务
    participant P as 权限服务
    
    U->>F: 输入用户名密码
    F->>G: POST /api/v1/iam/auth/login
    G->>A: 调用认证服务
    A->>A: 验证用户名密码
    A-->>G: 返回JWT Token
    G-->>F: 返回Token
    F->>G: GET /api/v1/iam/users/me/permissions
    G->>P: 调用权限服务
    P->>P: 查询用户权限
    P-->>G: 返回权限信息
    G-->>F: 返回权限数据
    F->>F: 渲染菜单和界面
```

#### **6.2. 流程二：创建项目并添加成员**

**流程概述**
用户创建新项目，设置项目基本信息，然后添加团队成员并分配角色。

**时序图**
```mermaid
sequenceDiagram
    participant U as 用户
    participant F as 前端
    participant G as 网关
    participant P as 项目服务
    participant N as 通知服务
    
    U->>F: 点击创建项目
    F->>G: POST /api/v1/projects
    G->>P: 调用项目服务
    P->>P: 创建项目记录
    P-->>G: 返回项目信息
    G-->>F: 返回创建结果
    U->>F: 添加项目成员
    F->>G: POST /api/v1/projects/{id}/members
    G->>P: 调用项目服务
    P->>P: 添加成员记录
    P->>N: 发送通知事件
    P-->>G: 返回添加结果
    G-->>F: 返回添加结果
    N->>N: 发送邮件/站内信通知
```

#### **6.3. 流程三：创建并发布一个数据同步工作流**

**流程概述**
用户定义数据同步任务，配置源和目标数据源，设置同步策略，然后发布工作流。

**时序图**
```mermaid
sequenceDiagram
    participant U as 用户
    participant F as 前端
    participant G as 网关
    participant W as 工作流服务
    participant I as 集成服务
    
    U->>F: 定义同步任务
    F->>G: POST /api/v1/workflow/definitions
    G->>W: 调用工作流服务
    W->>W: 创建工作流定义
    W-->>G: 返回定义信息
    G-->>F: 返回创建结果
    U->>F: 配置数据源
    F->>G: POST /api/v1/integration/datasources
    G->>I: 调用集成服务
    I->>I: 创建数据源配置
    I-->>G: 返回数据源信息
    G-->>F: 返回配置结果
    U->>F: 发布工作流
    F->>G: POST /api/v1/workflow/definitions/{id}/publish
    G->>W: 调用工作流服务
    W->>W: 发布工作流定义
    W-->>G: 返回发布结果
    G-->>F: 返回发布结果
```

#### **6.4. 流程四：零代码应用构建与部署**

**流程概述**
用户通过拖拽方式构建应用界面，配置数据模型和业务逻辑，最后部署应用供使用。

**时序图**
```mermaid
sequenceDiagram
    participant U as 用户
    participant F as 前端
    participant G as 网关
    participant Z as 零代码服务
    participant D as 部署服务
    
    U->>F: 拖拽构建应用
    F->>G: POST /api/v1/zerocode/applications
    G->>Z: 调用零代码服务
    Z->>Z: 创建应用元数据
    Z-->>G: 返回应用信息
    G-->>F: 返回创建结果
    U->>F: 配置数据模型
    F->>G: PUT /api/v1/zerocode/applications/{id}
    G->>Z: 调用零代码服务
    Z->>Z: 更新应用配置
    Z-->>G: 返回更新结果
    G-->>F: 返回更新结果
    U->>F: 部署应用
    F->>G: POST /api/v1/zerocode/applications/{id}/deploy
    G->>Z: 调用零代码服务
    Z->>Z: 部署应用
    Z->>D: 调用部署服务
    D->>D: 部署应用实例
    D-->>Z: 返回部署结果
    Z-->>G: 返回部署结果
    G-->>F: 返回部署结果
```

### **7. 前端设计概要**

#### **7.1. 前端架构模式**

采用Vue 3 + TypeScript + Vite的技术栈，使用Pinia进行状态管理，Element Plus作为UI组件库。

#### **7.2. 核心目录结构**

```
src/
├── api/              # API接口
├── assets/           # 静态资源
├── components/       # 公共组件
├── composables/      # 组合式函数
├── layouts/          # 页面布局
├── locales/          # 国际化
├── modules/          # 功能模块
├── pages/            # 页面组件
├── plugins/          # 插件
├── router/           # 路由配置
├── stores/           # 状态管理
├── styles/           # 样式文件
├── types/            # 类型定义
├── utils/            # 工具函数
└── App.vue           # 根组件
```

#### **7.3. 权限控制方案**

基于RBAC模型，通过路由守卫和组件级权限控制实现细粒度权限管理。

#### **7.4. 状态管理**

使用Pinia进行全局状态管理，每个模块拥有独立的store。

#### **7.5. 组件设计规范**

遵循Element Plus设计规范，统一组件风格和交互方式。

### **8. 非功能性需求设计**

#### **8.1. 性能与扩展性**

- 响应时间：95%的请求响应时间小于1秒
- 并发用户：支持10000并发用户在线
- 扩展性：支持水平扩展，可通过增加节点提升处理能力

#### **8.2. 高可用与容灾**

- 服务可用性：99.9%
- 数据备份：每日自动备份，支持手动备份
- 容灾恢复：支持快速恢复，RTO<30分钟

#### **8.3. 安全性**

- 认证授权：OAuth2 + JWT
- 数据加密：敏感数据加密存储
- 安全审计：完整的操作日志记录

#### **8.4. 监控与运维**

- 系统监控：Prometheus + Grafana
- 链路追踪：OpenTelemetry + Jaeger
- 日志管理：ELK Stack

### **9. 开发规范与工具链**

#### **9.1. 代码规范**

遵循Google Java Style Guide和Vue官方编码规范。

#### **9.2. 分支管理策略**

采用Git Flow分支管理模型。

#### **9.3. CI/CD流程**

使用GitLab CI/CD实现自动化构建和部署。

#### **9.4. 测试策略**

- 单元测试：覆盖率不低于80%
- 集成测试：核心业务流程100%覆盖
- 端到端测试：关键用户场景100%覆盖

### **10. 系统安全设计**

#### **10.1. 身份认证**
- 基于OAuth2.0+JWT实现认证
- 支持多因素认证
- 密码策略：复杂度要求、定期更换、历史密码限制
- 会话管理：超时控制、并发登录限制

#### **10.2. 权限控制**
- RBAC+ABAC混合权限模型
- 数据级权限：行级数据隔离
- 功能级权限：菜单、按钮、接口权限
- 字段级权限：表单字段访问控制

#### **10.3. 数据安全**
- 敏感数据加密存储（如密码、身份证号）
- Docker镜像漏洞自动扫描（集成Trivy实现供应链安全增强）
- 传输加密（TLS 1.3）
- 数据脱敏（日志、查询结果）
- 数据备份与恢复策略

#### **10.4. 操作审计**
- 全量操作日志记录
- 敏感操作双人授权
- 操作轨迹追踪
- 审计日志防篡改

### **11. 高可用设计**

#### **11.1. 集群部署**
- 无状态服务水平扩展
- 数据库主从复制
- Redis集群
- 消息队列集群

#### **11.2. 限流熔断**
- API网关限流
- 服务级限流（Redis+Lua）
- 熔断降级（Resilience4j）
- 队列削峰填谷

#### **11.3. 缓存策略**
- 多级缓存：本地缓存+分布式缓存
- 热点数据缓存
- 缓存预热与更新
- 缓存穿透/击穿/雪崩防护

#### **11.4. 故障恢复**
- 服务自愈（健康检查+自动重启）
- 数据恢复机制
- 灾难备份
- 故障演练

### **12. 性能优化**

#### **12.1. 数据库优化**
- 合理索引设计
- SQL优化
- 分库分表（ShardingSphere）
- 读写分离

#### **12.2. 应用优化**
- 异步处理
- 批量操作
- 延迟加载
- 资源池化（线程池、连接池）

#### **12.3. 前端优化**
- 启用Vue 3.6 Vapor Mode提升渲染性能，复杂列表场景性能提升300%
- 资源压缩与合并
- 懒加载
- 缓存策略
- 预渲染

### **13. 多语言与国际化**

#### **13.1. 多语言支持**
- 系统界面多语言（zh-CN, en-US, ja-JP, ko-KR）
- 动态语言切换
- 语言包管理与更新
- 第三方组件国际化适配

#### **13.2. 多时区支持**
- 用户时区设置
- 时间自动转换
- 日志时间标准化

### **14. 集成与扩展**

#### **14.1. 插件机制**
- 流程插件：自定义节点、自定义连线
- 表单插件：自定义字段类型
- 通知插件：自定义通知渠道

#### **14.2. API设计**
- RESTful API设计规范
- 版本控制
- 接口文档（OpenAPI）
- 接口测试工具

#### **14.3. 事件驱动**
- 领域事件发布订阅
- 事件总线
- 事件溯源

### **15. 系统工程架构设计**

#### **15.1. 后端工程结构**

系统采用前后端工程分离：
- 后端：Spring Boot + Spring Cloud + MyBatis Plus + PostgreSQL + Redis + RabbitMQ
- 前端：Vue 3 + Element Plus + Axios + ECharts
  前端工程名称：udap-ui
  后端工程名：udap
  基础包名：org.udap

```markdown
udap (父工程)
├── udap-core (核心模块)
├── udap-gateway (网关服务)
├── udap-micro (微服务模块)
│   ├── udap-system (系统服务)
│   ├── udap-auth (权限中心服务)
│   ├── udap-monitor (监控服务)
│   ├── udap-flow-engine (流程引擎服务)
│   ├── udap-flow-execution (流程执行服务)
│   ├── udap-job (调度任务服务)
│   ├── udap-integration (集成服务)
│   ├── udap-report (报表服务)
│   └── udap-design-platform (开发平台服务)
├── udap-api (API模块)
│   ├── udap-common (API需要公共的实体、DTO、对象等)
│   ├── udap-inner-api (对内提供远程调用服务API)
│   └── udap-open-api (对外开放API)
└── udap-standalone (单体服务)
```

#### **15.2. 前端工程结构**

```markdown
src/
├── assets/                  # 静态资源
│   ├── svgs/                # SVG图标
│   ├── styles/              # 全局样式
│   │   ├── variables.scss   # SCSS变量
│   │   ├── mixins.scss      # SCSS混入
│   │   └── global.scss      # 全局样式
│   └── images/              # 图片资源
├── components/              # 通用组件
│   ├── layout/              # 布局组件
│   │   ├── AppLayout.vue    # 主布局
│   │   ├── AppHeader.vue    # 顶部导航
│   │   └── AppSidebar.vue   # 侧边菜单
│   ├── common/              # 通用组件
│   │   ├── PageHeader.vue   # 页面标题
│   │   ├── SearchBar.vue    # 搜索栏
│   │   └── SvgIcon.vue      # SVG图标组件
│   └── business/            # 业务组件
│       ├── flow/
│       │   ├── FlowDesigner.vue  # 流程设计器
│       │   └── ApprovalPanel.vue    # 审批面板
│       └── system/
│           ├── TenantSelector.vue   # 租户选择器
│           └── RoleAssignment.vue   # 角色分配
├── composables/             # 组合式函数
│   ├── useAxios.ts          # Axios封装
│   ├── useForm.ts           # 表单处理
│   ├── useI18n.ts          # 多语言扩展
│   └── usePermission.ts     # 权限验证
├── router/                  # 路由管理
│   ├── index.ts            # 路由入口
│   ├── routes.ts            # 路由配置
│   └── guard/               # 路由守卫
│       ├── auth.ts          # 认证守卫
│       └── permission.ts    # 权限守卫
├── stores/                  # Pinia状态管理
│   ├── auth.ts              # 认证状态
│   ├── permission.ts        # 权限状态
│   ├── workflow.ts          # 流程引擎状态
│   └── system.ts            # 系统管理状态
├── utils/                   # 工具函数
│   ├── request.ts           # Axios封装
│   ├── auth.ts              # 认证工具
│   ├── validate.ts          # 验证工具
│   └── formatter.ts         # 数据格式化
├── views/                   # 页面视图
│   ├── login/               # 登录模块
│   │   └── LoginPage.vue
│   ├── dashboard/           # 仪表盘
│   │   ├── index.vue
│   │   └── components/
│   ├── flow/            # 流程引擎
│   │   ├── design/          # 流程设计
│   │   ├── execution/       # 流程执行
│   │   └── monitoring/      # 流程监控
│   ├── system/              # 系统管理
│   │   ├── tenant/          # 租户管理
│   │   ├── user/            # 用户管理
│   │   ├── role/            # 角色权限
│   │   └── dict/            # 数据字典
│   └── monitor/             # 系统监控
│       ├── audit/           # 操作审计
│       ├── log/             # 日志分析
│       └── dashboard/       # 监控大屏
├── api/                     # API接口管理
│   ├── auth.ts              # 认证相关API
│   ├── workflow.ts          # 流程引擎API
│   ├── system.ts            # 系统管理API
│   └── monitor.ts           # 监控API
├── i18n/                 # 国际化语言包
│   ├── zh-CN.ts
│   ├── en-US.ts
│   └── ja-JP.ts
├── App.vue                  # 根组件
└── main.ts                  # 应用入口
```

#### **15.3. 服务间依赖关系**

##### **服务依赖矩阵**
| 依赖方 \ 被依赖方 | 系统服务 | 权限中心 | 日志服务 | 流程引擎 | 流程执行 | 调度任务 | 集成服务 | 报表服务 | 开发平台 |
|-------------------|----------|----------|----------|----------|----------|----------|----------|----------|----------|
| **系统服务**      | -        | ✅        | ✅        |          |          |          |          |          |          |
| **权限中心**      | ✅        | -        | ✅        |          |          |          |          |          |          |
| **日志服务**      |          |          | -        |          |          |          |          |          |          |
| **流程引擎**      | ✅        | ✅        | ✅        | -        |          |          |          |          |          |
| **流程执行**      |          | ✅        | ✅        | ✅        | -        | ✅        | ✅        |          |          |
| **调度任务**      |          |          | ✅        |          | ✅        | -        | ✅        |          |          |
| **集成服务**      | ✅        | ✅        | ✅        |          |          |          | -        |          |          |
| **报表服务**      |          |          | ✅        |          | ✅        | ✅        |          | -        |          |
| **开发平台**      | ✅        | ✅        | ✅        |          |          |          | ✅        |          | -        |

#### **15.4. 部署与运维**

##### **环境规划**
- 开发环境（dev）
- 测试环境（test）
- 预发布环境（staging）
- 生产环境（prod）

##### **部署流程**
- 代码管理：GitLab
- 持续集成：自动化构建、测试
- 持续部署：蓝绿部署、灰度发布，基于Jenkins Pipeline-as-Code实现，核心流程包括：
  ```groovy
  pipeline {
    agent any
    stages {
      stage('Build') { steps { sh 'mvn clean package' } }
      stage('Test') { steps { sh 'mvn test' } }
      stage('Scan') { steps { sh 'trivy image $APP_IMAGE' } }
      stage('Deploy') { steps { sh 'kubectl apply -f deployment.yaml' } }
    }
  }
  ```
- 配置管理：Nacos配置中心

##### **监控告警**
- 系统监控：CPU、内存、磁盘、网络
- 应用监控：响应时间、错误率、吞吐量
- 业务监控：流程数量、任务数量、活跃用户
- 告警策略：多级别、多渠道、告警升级

##### **交付物清单**
- 系统概要设计文档
- 系统详细设计文档
- 系统数据库设计文档
- 源代码及构建脚本
- 数据库脚本
- 部署文档
- 用户手册
- 测试报告
- 运维手册
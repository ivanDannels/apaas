# APaaS 平台领域模块

APaaS 平台领域模块（apaas-domain）是整个平台的核心模块，基于领域驱动设计（DDD）架构模式构建，包含了各个业务模块共用的领域层功能和配置。

## 功能特性

- 统一的异常处理机制
- 通用的工具类（JwtUtils、RedisUtils 等）
- 基础实体类和仓储类（BaseEntity、BaseRepository 等）
- 通用配置类（Redis 配置、JPA 配置等）
- 安全配置（Spring Security）
- Web 配置
- OpenAPI 配置
- DTO基类和Assembler基类
- 应用服务接口和抽象实现

## 响应式重构说明

本模块已按照 Spring Boot WebFlux 响应式模式和 DDD 领域驱动架构进行重构，主要变更包括：

1. **领域层**:

   - BaseEntity 实体类已适配响应式数据访问
   - BaseRepository 仓储接口已更新为响应式模式，使用 Mono/Flux 作为返回类型
   - 新增了对 R2DBC 的支持，提供响应式数据库访问能力
   - 领域事件相关类已适配响应式模式

2. **应用层**:

   - ApplicationService 应用服务接口定义了应用层通用操作
   - AbstractApplicationService 抽象应用服务实现提供了基础功能实现
   - DTO基类和Assembler基类，用于实体与DTO之间的转换
   - ApplicationService接口和AbstractApplicationService实现类不再限定实体类型，提高了灵活性

3. **基础设施层**:

   - 通用配置类（Redis 配置、Web 配置等）已适配响应式模式
   - 统一的异常处理机制
   - 日志工具类和分布式锁服务

4. **接口层**:
   - 提供了响应式控制器基类 BaseRest

## 包结构说明

```
org.apaas
├── application          # 应用层
│   ├── assembler        # 装配器（实体与DTO转换）
│   ├── dto              # 数据传输对象
│   └── service          # 应用服务接口和实现
├── domain               # 领域层
│   ├── aggregate        # 聚合根
│   ├── entity           # 实体类
│   ├── event            # 领域事件
│   ├── exception        # 领域异常
│   ├── factory          # 工厂类
│   ├── repository       # 仓储接口
│   ├── service          # 领域服务
│   ├── specification    # 规约模式
│   └── vo               # 值对象
├── infrastructure       # 基础设施层
│   ├── config           # 配置类
│   ├── convert          # 转换器
│   ├── exception       # 异常处理
│   ├── interceptor     # 拦截器
│   ├── lock            # 分布式锁
│   ├── log             # 日志工具
│   └── utils           # 工具类
└── interfaces          # 接口层
    ├── event           # 事件监听器
    └── rest            # REST控制器基类
```

## 核心组件

### 1. 领域层核心组件

- **BaseEntity**: 基础实体类，所有实体都需要继承，包含通用字段如ID、租户ID、创建时间等
- **BaseRepository**: 基础仓储接口，继承自 R2dbcRepository，提供响应式数据访问能力
- **领域事件**: 包含事件发布服务和相关事件类

### 2. 应用层核心组件

- **BaseDTO**: 所有DTO的基类，包含通用字段如ID、租户ID、创建时间等
- **BaseAssembler**: 实体与DTO之间的转换接口
- **ApplicationService**: 应用服务接口，定义应用层通用操作
- **AbstractApplicationService**: 抽象应用服务实现，提供基础功能实现

### 3. 基础设施层核心组件

- **配置类**: Redis配置、Web配置、安全配置等
- **工具类**: 日志工具、分布式锁、IP工具等
- **异常处理**: 全局异常处理器

### 4. 接口层核心组件

- **BaseRest**: 响应式控制器基类，提供通用的REST操作

## 使用方法

1. **领域层使用**:

   - 继承 BaseEntity 创建领域实体
   - 继承 BaseRepository 接口获取基础数据访问能力

2. **应用层使用**:

   - 继承 BaseDTO 创建应用层数据传输对象
   - 实现 BaseAssembler 或直接实现实体与DTO之间的转换
   - 继承 AbstractApplicationService 实现应用服务

3. **接口层使用**:

   - 继承 BaseRest 创建REST控制器

4. **基础设施层使用**:

   - 直接使用配置类和工具类

## 依赖说明

- spring-boot-starter-webflux: 响应式 Web 框架
- spring-boot-starter-data-redis-reactive: 响应式 Redis 数据访问
- spring-boot-starter-data-r2dbc: 响应式数据库访问
- spring-boot-starter-security: 安全框架
- springdoc-openapi-starter-webflux-ui: OpenAPI 文档
- redisson-spring-boot-starter: Redisson 客户端
- spring-cloud-starter-loadbalancer: 负载均衡
- spring-cloud-starter-alibaba-nacos-discovery: Nacos 服务发现
- spring-cloud-starter-alibaba-nacos-config: Nacos 配置中心

## 测试说明

本模块包含了完整的测试代码，覆盖了 DDD 领域驱动的每一层：

- **实体层测试**: BaseEntityTest.java
- **仓储层测试**: BaseRepositoryTest.java
- **服务层测试**: AbstractApplicationServiceTest.java
- **接口层测试**: BaseRestTest.java

测试使用了 Testcontainers 来提供真实的数据库环境，确保测试的准确性。

## 设计优势

1. **领域驱动设计**: 严格按照DDD分层架构设计，分离关注点
2. **响应式编程**: 全面采用响应式编程模型，提高系统性能和可扩展性
3. **高内聚低耦合**: 各层职责清晰，组件之间依赖关系明确
4. **可扩展性强**: 抽象接口和基类设计，便于业务扩展
5. **租户隔离**: 内置租户ID支持，便于多租户系统实现
6. **统一异常处理**: 全局异常处理机制，提高系统健壮性